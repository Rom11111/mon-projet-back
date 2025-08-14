package org.romain.demo2.exception;

/**
 * Exception que j'utilise pour les erreurs métier (logique fonctionnelle).
 * Exemple : si un produit est déjà réservé ou si un client essaie de faire quelque chose qu’il ne peut pas.
 * Cette erreur est captée globalement pour envoyer un message propre au front.
 */
public class BusinessException extends RuntimeException {

    /**
     * Constructeur : je passe juste un message d'erreur qui sera retourné dans la réponse API.
     * Le message est aussi loggable si besoin.
     *
     * @param message le message d'erreur métier
     */
    public BusinessException(String message) {
        super(message); // je passe le message à RuntimeException
    }
}
