package org.romain.demo2.model;

import lombok.Getter;

/**
 * Représente les différents états possibles d'un utilisateur dans le système.
 */
@Getter
public enum UserStatus {
    ACTIVE("Utilisateur actif"),
    INACTIVE("Utilisateur désactivé"),
    PENDING("En attente de vérification"),
    BLOCKED("Compte bloqué");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public boolean isAccessible() {
        return this == ACTIVE;
    }

    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }

    public boolean isInactive() {
        return this == INACTIVE;
    }
}