package org.romain.demo2.exception;

// Exception que je lève quand une ressource n’est pas trouvée (ex : produit, user...).
// Elle est captée globalement pour retourner un message propre au front.
public class ResourceNotFoundException extends RuntimeException {

    // Constructeur qui prend un message personnalisé
    // → Ce message sera affiché dans la réponse JSON ou dans les logs
    public ResourceNotFoundException(String message) {
        super(message); // j'appelle le constructeur de RuntimeException avec mon message
    }
}
