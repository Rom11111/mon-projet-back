package org.romain.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;

import org.romain.demo2.model.Product;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class Demo2ApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        // On protège au cas où le contexte n’a pas initialisé mvc
        if (this.mvc == null) {
            this.mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
        }
    }

    @Test
    @WithUserDetails("b@b.com")
        // Obligatoire si le contrôleur utilise @AuthenticationPrincipal et vérifie un rôle CLIENT
    void getAllProductAsClient_shouldBe200ok() throws Exception {
        mvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("d@d.com")
        // Un client qui essaie de supprimer doit être refusé
    void deleteProductAsClient_shouldBe403Forbidden() throws Exception {
        mvc.perform(delete("/api/product/1").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getClientWithId2_shouldHideSensitiveFields() throws Exception {
        // On vérifie que certaines infos ne sortent pas (JsonView côté controller)
        mvc.perform(get("/api/client/2"))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.number").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @WithUserDetails("c@c.com")
        // Tech mais pas admin : chez toi, la route est @IsTech (accès OK),
        // puis la logique interne autorise seulement ADMIN ou créateur.
        // Si l’utilisateur c@c.com est créateur du produit 6 → 200 OK,
        // sinon → 403. Adapte l’assert selon tes données mock.
    void deleteAsTechDependingOnOwnership_shouldMatchControllerLogic() throws Exception {
        mvc.perform(delete("/api/product/6").with(csrf()))
                .andExpect(status().isOk()); // mets isForbidden() si le TECH n’est pas owner dans tes données
    }

    @Test
    @WithUserDetails("a@a.com")
    void addNewProductWithMandatoryFields_shouldBe201created() throws Exception {
        // price doit être en BigDecimal, pas en float
        Product product = new Product();
        product.setName("Test");
        product.setCode("Test");
        product.setPrice(new BigDecimal("0.11")); // BigDecimal attendu côté entité

        // La méthode POST /api/product attend un multipart :
        // - part "product" = JSON du produit
        // - part "photo" optionnelle (on n’en envoie pas ici)
        byte[] productJson = mapper.writeValueAsBytes(product);

        // On crée une part "product" au format JSON
        MockMultipartFile productPart = new MockMultipartFile(
                "product",              // doit correspondre à @RequestPart("product")
                "product.json",
                "application/json",
                productJson
        );

        mvc.perform(multipart("/api/product")
                        .file(productPart)
                        .with(csrf())) // on ajoute le CSRF si activé
                .andExpect(status().isCreated())
                // Optionnel : on vérifie qu’un champ revient bien
                .andExpect(jsonPath("$.name").value("Test"));
    }
}
