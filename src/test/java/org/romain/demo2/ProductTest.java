package org.romain.demo2;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.model.Product;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private Validator validator;

    // Je crée un Validator avant chaque test (try-with-resources pour éviter le warning)
    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    // Cas "valide" minimal : je remplis le strict nécessaire (name, code, price, stock)
    @Test
    void createValidProduct_shouldBeValid() {
        Product p = new Product();
        p.setName("Test");                        // @NotBlank
        p.setCode("TEST-001");                    // @Size(min=3) + @NotBlank
        p.setPrice(new BigDecimal("10.00"));      // @DecimalMin("0.1")
        p.setStock(1);                            // @NotNull + @Min(0)

        Set<ConstraintViolation<Product>> violations = validator.validate(p);

        // Utile en debug pour voir ce qui bloque si jamais ça échoue
        if (!violations.isEmpty()) {
            violations.forEach(v -> System.out.println(
                    v.getPropertyPath() + " -> @" +
                            v.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName() +
                            " : " + v.getMessage() + " (valeur=" + v.getInvalidValue() + ")"
            ));
        }

        assertTrue(violations.isEmpty(), "Des contraintes ne sont pas respectées (voir la console).");
    }

    // Sans name -> doit déclencher @NotBlank sur "name"
    @Test
    void createProductWithoutName_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setCode("TEST-001");               // j'évite une erreur sur "code"
        productTest.setPrice(BigDecimal.valueOf(10));  // j'évite une erreur sur "price"
        productTest.setStock(1);                       // j'évite une erreur sur "stock"

        Set<ConstraintViolation<Product>> violations = validator.validate(productTest);

        boolean notBlankViolationExist = TestUtils.constraintExist(
                violations, "name", "NotBlank");
        assertTrue(notBlankViolationExist);
    }

    // Sans code -> doit déclencher @NotBlank sur "code"
    @Test
    void createProductWithoutCode_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setName("Test");
        productTest.setPrice(BigDecimal.valueOf(10));
        productTest.setStock(1);

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(productTest),
                        "code",
                        "NotBlank"));
    }


    // Code trop court -> doit déclencher @Size(min=3)
    @Test
    void createProductWithCodeTooShort_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setName("Test");
        productTest.setPrice(BigDecimal.TEN);
        productTest.setStock(1);
        productTest.setCode("a"); // longueur 1 < min 3

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(productTest),
                        "code",
                        "Size") //on vérifie le nom de l’annotation, pas le message
        );
    }


    // Prix négatif -> doit déclencher @DecimalMin("0.1")
    @Test
    void createProductWithNegativePrice_shouldNotBeValid() {
        Product produitTest = new Product();
        produitTest.setName("test");
        produitTest.setCode("TEST-001");
        produitTest.setStock(1);
        produitTest.setPrice(BigDecimal.valueOf(-10)); // négatif

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(produitTest),
                        "price",
                        "DecimalMin"));
    }

    // (Optionnel) Prix = 0 -> doit aussi échouer avec @DecimalMin("0.1") (borne)
    @Test
    void createProductWithZeroPrice_shouldNotBeValid() {
        Product produitTest = new Product();
        produitTest.setName("test");
        produitTest.setCode("TEST-001");
        produitTest.setStock(1);
        produitTest.setPrice(BigDecimal.ZERO); // 0 < 0.1

        assertTrue(
                TestUtils.constraintExist(
                        validator.validate(produitTest),
                        "price",
                        "DecimalMin"));
    }
}
