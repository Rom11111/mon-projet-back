package org.romain.demo2;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class TestUtils {

    /**
     * Vérifie si une contrainte donnée existe sur un champ spécifique
     * dans la liste des violations.
     *
     * @param violations   Set de violations retourné par Validator
     * @param fieldName    le nom du champ (ex: "name", "code")
     * @param constraintName le nom de l'annotation de validation (ex: "NotBlank", "Size", "DecimalMin")
     * @return true si la contrainte est présente pour ce champ
     */
    public static <T> boolean constraintExist(Set<ConstraintViolation<T>> violations,
                                              String fieldName,
                                              String constraintName) {
        return violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals(fieldName))
                .map(v -> v.getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .getSimpleName())
                .anyMatch(s -> s.equals(constraintName));
    }
}
