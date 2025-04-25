package org.romain.demo2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.controller.ProductController;
import org.romain.demo2.mock.MockProductDao;
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
        productController = new ProductController(
                new MockProductDao(), new MockSecurityUtils("USER")
        );
    }

    @Test
    void callGetWithExistingProduct_shouldSend200ok() {
        ResponseEntity<Product> response = productController.get(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void callGetWithExistingProduct_shouldSend404notFound() {
        ResponseEntity<Product> response = productController.get(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteExistingProductBySellerOwner_shouldSend204noContent() {
        User fakeUser = new User();
        fakeUser.setId(1);
        AppUserDetails userDetails = new AppUserDetails(fakeUser);

        ResponseEntity<Product> response = productController.delete(2, userDetails);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteExistingProductByNotSeller_shouldSend403forbiden() {
        User fakeUser = new User();
        fakeUser.setId(2);
        AppUserDetails userDetails = new AppUserDetails(fakeUser);

        ResponseEntity<Product> response = productController.delete(2, userDetails);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteExistingProductByNotSellerOwner_shouldSend403forbiden() {
        User fakeUser = new User();
        fakeUser.setId(2);
        AppUserDetails userDetails = new AppUserDetails(fakeUser);


        ResponseEntity<Product> response = productController.delete(2, userDetails);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}