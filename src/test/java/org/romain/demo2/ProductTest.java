package org.romain.demo2;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.model.Product;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createValidProduct_shouldBeValid() {

        Product productTest = new Product();
        productTest.setPrice(10);
        productTest.setCode("Test");
        productTest.setName("Test");

        Set<ConstraintViolation<Product>> violations = validator.validate(productTest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void createProductWithoutName_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setPrice(10);
        Set<ConstraintViolation<Object>> violations = validator.validate(productTest);

        boolean notBlankViolationExist = TestUtils.constraintExist(
                violations, "name", "NotBlank");

        assertTrue(notBlankViolationExist);
    }

    @Test
    void createProductWithoutCode_shouldNotBeValid() {
        Product productTest = new Product();

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(productTest),
                        "code",
                        "NotBlank"));
    }

    @Test
    void createProductWithCodeTooShort_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setCode("a");

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(productTest),
                        "code",
                        "Length"));

    }

    @Test
    void createProductWithNegativePrice_shouldNotBeValid() {
        Product produitTest = new Product();
        produitTest.setName("test");
        produitTest.setPrice(-10);

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(produitTest),
                        "price",
                        "DecimalMin"));

    }
}

