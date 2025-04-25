package org.romain.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class Demo2ApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getAllLabels_shouldBe200ok() throws Exception {

        mvc.perform(get("/labels"))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails("b@b.com")
        // obligatoire si le controleur utilise @AuthenticatiopPrincipal
    void getAllProductAsClient_shouldBe200ok() throws Exception {

        mvc.perform(get("/products"))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails("d@d.com")
        // obligatoire si le controleur utilise @AuthenticatiopPrincipal
    void deleteProductAsClient_shouldBe403ok() throws Exception {

        mvc.perform(delete("/product/1"))
                .andExpect(status().isForbidden());

    }

    @Test
    void getClientWithId2_shouldIncludeOnlyNeededInformationP() throws Exception {

        mvc.perform(get("/client/2"))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.number").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist());

    }

    @Test
    @WithUserDetails("c@c.com")
        // obligatoire si le controleur utilise @AuthenticatiopPrincipal
    void deleteAsTechButNotAdmin_shouldBe204NoContent() throws Exception {

        mvc.perform(delete("/product/6"))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithUserDetails("a@a.com")
    void addNewProductWithMandatoryFields_shouldBe201created() throws Exception {
        Product product = new Product();
        product.setName("Test");
        product.setCode("Test");
        product.setPrice(0.11f);
        String jsonProduct = mapper.writeValueAsString(product);

        mvc.perform(
                        post("/product")
                                .contentType("application/json")
                                .content(jsonProduct)
                )
                .andExpect(status().isCreated());

    }
}


