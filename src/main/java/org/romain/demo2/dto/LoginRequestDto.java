package org.romain.demo2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO utilisé pour la connexion d’un utilisateur.
 * Contient uniquement l’email et le mot de passe.
 */
public record LoginRequestDto(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}

