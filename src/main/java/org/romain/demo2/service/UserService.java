package org.romain.demo2.service;

import org.romain.demo2.dao.UserDao;
import org.romain.demo2.model.Role;
import org.romain.demo2.model.User;
import org.romain.demo2.model.UserStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Désactive un utilisateur (soft delete).
     * TECH et ADMIN peuvent faire ça, mais :
     * - Un TECH ne peut pas désactiver un autre TECH
     * - Personne ne peut désactiver un ADMIN
     * - Si l'utilisateur est déjà inactif, on le dit
     *
     * Je retourne un ResponseEntity<?> parce que je peux avoir :
     * - un noContent() (204) si OK
     * - un message (String) si erreur
     */
    public ResponseEntity<?> deactivateUser(User currentUser, int targetId) {
        return userDao.findById(targetId)
                .map(targetUser -> {
                    // Cas interdit : tentative de désactivation d'un admin
                    if (targetUser.getRole() == Role.ADMIN) {
                        return forbidden("Impossible de désactiver un administrateur");
                    }
                    // Cas interdit : TECH ne peut désactiver un autre TECH
                    if (currentUser.getRole() == Role.TECH && targetUser.getRole() == Role.TECH) {
                        return forbidden("Un technicien ne peut pas désactiver un autre technicien");
                    }
                    // Déjà inactif
                    if (targetUser.getUserStatus() == UserStatus.INACTIVE) {
                        return bad("L'utilisateur est déjà inactif");
                    }

                    // On passe l'utilisateur à l'état inactif
                    targetUser.setUserStatus(UserStatus.INACTIVE);
                    userDao.save(targetUser);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime définitivement un utilisateur (hard delete).
     * Utilisé uniquement par les administrateurs.
     */
    public ResponseEntity<Void> deleteUserPermanently(int targetId) {
        if (!userDao.existsById(targetId)) {
            return ResponseEntity.notFound().build();
        }

        userDao.deleteById(targetId);
        return ResponseEntity.noContent().build();
    }

    // Helpers internes pour renvoyer des erreurs avec message personnalisé
    private ResponseEntity<String> forbidden(String message) {
        return ResponseEntity.status(403).body(message);
    }

    private ResponseEntity<String> bad(String message) {
        return ResponseEntity.badRequest().body(message);
    }
}
