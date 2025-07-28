package org.romain.demo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO générique pour les réponses d'API.
 * Permet d'envoyer un message + n'importe quel type de données (Rental, User, etc.).
 * Exemple de réponse :
 * {
 *   "message": "Opération réussie",
 *   "data": { ... } // peut être un Rental, un User, null, etc.
 * }
 */
@Data // Génère les getters, setters, toString, equals, hashCode
@AllArgsConstructor // Génère un constructeur avec tous les champs
public class ApiResponseDto<T> {

    // Message explicatif à retourner (succès, erreur, confirmation...)
    private String message;
    // Données liées à la réponse (objet créé, modifié, supprimé, etc.)
    private T data;
}
