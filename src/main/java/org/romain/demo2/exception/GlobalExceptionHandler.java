package org.romain.demo2.exception;

import org.romain.demo2.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gère globalement les exceptions et renvoie une réponse JSON claire à l'utilisateur.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Gère les erreurs métier (ex : produit déjà réservé)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBusiness(BusinessException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDto<>(e.getMessage(), null));
    }

    // Gère les erreurs de ressource introuvable (ex : produit inexistant)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDto<>(e.getMessage(), null));
    }

    // Gère les erreurs de validation (ex : champ manquant ou date invalide)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        // Je récupère les erreurs champ par champ en concaténant si nécessaire
        Map<String, String> errors = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        // clé = nom du champ
                        org.springframework.validation.FieldError::getField,
                        // valeur = message d'erreur (ou "inconnu" si null)
                        field -> field.getDefaultMessage() != null ? field.getDefaultMessage() : "Erreur inconnue",
                        // merge = je concatène les erreurs si le champ a plusieurs messages
                        (m1, m2) -> m1 + ", " + m2
                ));

        return ResponseEntity.badRequest()
                .body(new ApiResponseDto<>("Erreur de validation", errors));
    }

    // Gère toute autre erreur non prévue (fallback global)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneric(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDto<>("Erreur serveur : " + e.getMessage(), null));
    }
}

