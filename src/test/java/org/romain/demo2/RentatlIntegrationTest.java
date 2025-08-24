package org.romain.demo2;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.romain.demo2.model.User;
import org.romain.demo2.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.core.authority.AuthorityUtils;



/**
 * Tests d’intégration sur Rental
 * Ici on injecte manuellement un principal AppUserDetails dans la requête,
 * pour correspondre à la signature du controller (évite le NPE).
 */
@SpringBootTest
@AutoConfigureMockMvc
class RentalIntegrationTest {

    @Autowired
    private MockMvc mvc;

    // --- petite fabrique d’un Authentication avec AppUserDetails ---

    private UsernamePasswordAuthenticationToken adminAuth() {
        // on mock l’AppUserDetails attendu par le controller
        AppUserDetails principal = Mockito.mock(AppUserDetails.class);

        // on fournit un user non-null pour éviter le NPE dans le controller
        User admin = new User();
        admin.setId(999L);
        admin.setEmail("admin@test.com");
        Mockito.when(principal.getUser()).thenReturn(admin);
        Mockito.when(principal.getUsername()).thenReturn("admin@test.com");

        // on définit les rôles au niveau du token (c’est ce que Spring lit)
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // pas besoin de stubber principal.getAuthorities() → on laisse Mockito renvoyer null
        // Spring utilisera token.getAuthorities() ci-dessous
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    private UsernamePasswordAuthenticationToken clientAuth() {
        AppUserDetails principal = Mockito.mock(AppUserDetails.class);

        User client = new User();
        client.setId(123L);
        client.setEmail("client@test.com");
        Mockito.when(principal.getUser()).thenReturn(client);
        Mockito.when(principal.getUsername()).thenReturn("client@test.com");

        // rôle client directement sur le token
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));

        // idem : on ne stubbe pas principal.getAuthorities()
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }


    // 1) Lecture des rentals avec un ADMIN "connecté" → 200 OK
    @Test
    void getRentals_withValidLogin_shouldReturn200() throws Exception {
        mvc.perform(get("/api/rentals")
                        // on injecte notre Authentication contenant un AppUserDetails
                        .with(authentication(adminAuth())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    // 2) Création avec dates invalides côté métier (CLIENT connecté) → 409
    @Test
    void createRental_withInvalidDates_shouldReturn409() throws Exception {
        // startDate > endDate → doit déclencher ta BusinessException → 409
        String invalidJson = """
    {
      "productId": 1,
      "startDate": "2030-01-10",
      "endDate":   "2030-01-05",
      "quantity": 1
    }
    """;

        mvc.perform(post("/api/rentals/create")
                        .with(authentication(clientAuth()))       // on simule un CLIENT connecté
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isConflict()); // 409 attendu
    }
}
