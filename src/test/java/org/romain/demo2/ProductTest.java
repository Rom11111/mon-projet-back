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

    /**
     * Avant chaque test, je crée un Validator qui me permettra
     * de vérifier les contraintes de validation (comme @NotBlank, @Min, etc.)
     */
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Je teste un produit valide pour vérifier qu'il passe toutes les contraintes
     */
    @Test
    void createValidProduct_shouldBeValid() {
        Product productTest = new Product();
        productTest.setPrice(10);
        productTest.setCode("Test");
        productTest.setName("Test");

        // Je vérifie s’il y a des violations de contraintes
        Set<ConstraintViolation<Product>> violations = validator.validate(productTest);

        // Je m’assure que le produit est bien valide (aucune erreur attendue)
        assertTrue(violations.isEmpty());
    }

    /**
     * Je teste un produit sans nom → il devrait échouer à cause du @NotBlank sur le champ "name"
     */
    @Test
    void createProductWithoutName_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setPrice(10); // mais pas de nom !

        Set<ConstraintViolation<Object>> violations = validator.validate(productTest);

        // Je vérifie que l'erreur "NotBlank" est bien levée sur le champ "name"
        boolean notBlankViolationExist = TestUtils.constraintExist(
                violations, "name", "NotBlank");

        assertTrue(notBlankViolationExist);
    }

    /**
     * Je teste un produit sans code → doit échouer à cause de la contrainte @NotBlank sur "code"
     */
    @Test
    void createProductWithoutCode_shouldNotBeValid() {
        Product productTest = new Product(); // pas de code défini

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(productTest),
                        "code",
                        "NotBlank"));
    }

    /**
     * Je teste un code trop court → doit échouer à cause de @Length(min=...) ou @Size(min=...)
     */
    @Test
    void createProductWithCodeTooShort_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setCode("a"); // trop court !

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(productTest),
                        "code",
                        "Length")); // ou Size si tu utilises @Size dans ton entité
    }

    /**
     * Je teste un prix négatif → doit échouer à cause de @DecimalMin("0.0")
     */
    @Test
    void createProductWithNegativePrice_shouldNotBeValid() {
        Product produitTest = new Product();
        produitTest.setName("test");
        produitTest.setPrice(-10); // prix invalide

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(produitTest),
                        "price",
                        "DecimalMin")); // on attend une contrainte de minimum
    }
}
