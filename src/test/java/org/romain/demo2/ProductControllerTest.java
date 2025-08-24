package org.romain.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.controller.ProductController;
import org.romain.demo2.dto.ApiResponseDto;
import org.romain.demo2.mock.MockProductDao;
import org.romain.demo2.mock.MockProductService;
import org.romain.demo2.mock.MockSecurityUtils;
import org.romain.demo2.model.Product;
import org.romain.demo2.model.User;
import org.romain.demo2.security.AppUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductControllerTest {

    ProductController productController;

    @BeforeEach
    void setUp() {
        // On instancie le controller avec des "mocks" très simples
        // - MockProductDao : simule la BDD (id=1 existe, 999 n'existe pas par ex.)
        // - MockSecurityUtils("USER") : simule un utilisateur rôle "USER"
        // - serviceFile = null : non utilisé dans ces tests
        // - MockProductService : simule la logique de service (doit être public)
        productController = new ProductController(
                new MockProductDao(),
                new MockSecurityUtils("USER"),
                null,
                new MockProductService()
        );
    }

    @Test
    void callGetWithExistingProduct_shouldSend200ok() {
        // On demande un produit qui existe (ex: id=1 dans le MockProductDao)
        ResponseEntity<Product> response = productController.get(1L);

        // On s’attend à 200 OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Et à un body non nul
        Assertions.assertNotNull(response.getBody());
        // Petit check basique sur l’id
        Assertions.assertEquals(1L, response.getBody().getId());
    }

    @Test
    void callGetWithNotExistingProduct_shouldSend404notFound() {
        // On demande un produit qui n’existe pas (ex: id=999)
        ResponseEntity<Product> response = productController.get(999L);

        // On s’attend à 404 NOT_FOUND
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Et pas de body
        Assertions.assertNull(response.getBody());
    }

    @Test
    void deleteExistingProductBySellerOwner_shouldSend200ok() {
        // On simule un user "owner" du produit id=1 (ex: creatorId=1 dans le mock)
        User fakeUser = new User();
        fakeUser.setId(1L); // même id que le créateur pour autoriser la suppression
        AppUserDetails userDetails = new AppUserDetails(fakeUser);

        // ⚠️ Le controller s’appelle bien deleteProduct (pas delete)
        ResponseEntity<ApiResponseDto<Void>> response = productController.deleteProduct(1L, userDetails);

        // Le controller renvoie 200 OK avec un message dans ApiResponseDto
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Produit supprimé avec succès", response.getBody().getMessage());
    }

    @Test
    void deleteExistingProductByNotSeller_shouldSend403forbidden() {
        // On simule un user qui N’EST PAS le créateur (id=2 ≠ creatorId=1)
        User fakeUser = new User();
        fakeUser.setId(2L);
        AppUserDetails userDetails = new AppUserDetails(fakeUser);

        ResponseEntity<ApiResponseDto<Void>> response = productController.deleteProduct(1L, userDetails);

        // Comme ce n’est ni l’owner ni un admin, on reçoit 403 FORBIDDEN
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Suppression non autorisée", response.getBody().getMessage());
    }

    @Test
    void deleteNotExistingProduct_shouldSend404notFound() {
        // On tente de supprimer un produit qui n’existe pas
        User fakeUser = new User();
        fakeUser.setId(1L); // id quelconque
        AppUserDetails userDetails = new AppUserDetails(fakeUser);

        ResponseEntity<ApiResponseDto<Void>> response = productController.deleteProduct(999L, userDetails);

        // 404 NOT_FOUND attendu
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Produit introuvable", response.getBody().getMessage());
    }
}
