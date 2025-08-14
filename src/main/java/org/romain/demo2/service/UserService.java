package org.romain.demo2.service;

import org.romain.demo2.dao.UserDao;
import org.romain.demo2.dto.UserCreationDto;
import org.romain.demo2.model.Role;
import org.romain.demo2.model.User;
import org.romain.demo2.model.UserStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> createUser(UserCreationDto dto, User currentUser) {

        // Vérifie que l'email n'est pas déjà utilisé
        if (userDao.findByEmail(dto.getEmail()).isPresent()) {
            return bad("Un utilisateur avec cet email existe déjà");
        }

        // Règle : TECH ne peut créer que des CLIENTS
        if (currentUser.getRole() == Role.TECH &&
                (dto.getRole() == Role.TECH || dto.getRole() == Role.ADMIN)) {
            return forbidden("Un technicien ne peut créer que des utilisateurs CLIENT");
        }

        // Création de l'utilisateur
        User user = new User();
        user.setId(null); // Force à null pour éviter les insertions forcées
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Mot de passe hashé
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setCompany(dto.getCompany());
        user.setCompanyAddress(dto.getCompanyAddress());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setUserStatus(dto.getUserStatus());

        // Sauvegarde en base
        User saved = userDao.save(user);
        return ResponseEntity.status(201).body(saved);
    }

    /**
     * Désactive un utilisateur (soft delete).
     * TECH et ADMIN peuvent faire ça, mais :
     * - Un TECH ne peut pas désactiver un autre TECH
     * - Personne ne peut désactiver un ADMIN
     * - Si l'utilisateur est déjà inactif, on le dit
     * Je retourne un ResponseEntity<?> parce que je peux avoir :
     * - un noContent() (204) si OK
     * - un message (String) si erreur
     */
    public ResponseEntity<?> deactivateUser(User currentUser, Long targetId) {
        return userDao.findById(targetId)
                .map(targetUser -> {
                    if (targetUser.getRole() == Role.ADMIN) {
                        return forbidden("Impossible de désactiver un administrateur");
                    }
                    if (currentUser.getRole() == Role.TECH && targetUser.getRole() == Role.TECH) {
                        return forbidden("Un technicien ne peut pas désactiver un autre technicien");
                    }
                    if (targetUser.getUserStatus() == UserStatus.INACTIVE) {
                        return bad("L'utilisateur est déjà inactif");
                    }

                    targetUser.setUserStatus(UserStatus.INACTIVE);
                    userDao.save(targetUser);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> reactivateUser(User currentUser, Long targetId) {
        return userDao.findById(targetId)
                .map(targetUser -> {
                    if (targetUser.getRole() == Role.ADMIN) {
                        return forbidden("Impossible de réactiver un administrateur désactivé");
                    }
                    if (currentUser.getRole() == Role.TECH && targetUser.getRole() == Role.TECH) {
                        return forbidden("Un technicien ne peut pas réactiver un autre technicien");
                    }
                    if (targetUser.getUserStatus() == UserStatus.ACTIVE) {
                        return bad("L'utilisateur est déjà actif");
                    }

                    targetUser.setUserStatus(UserStatus.ACTIVE);
                    userDao.save(targetUser);
                    return ResponseEntity.ok("Utilisateur réactivé avec succès");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime définitivement un utilisateur (hard delete).
     * Utilisé uniquement par les administrateurs.
     *
     * @param targetId ID de l'utilisateur à supprimer
     * @return true si supprimé, false si utilisateur non trouvé
     */
    public boolean deleteUserPermanently(Long targetId) {
        if (!userDao.existsById(targetId)) {
            return false;
        }
        userDao.deleteById(targetId);
        return true;
    }

    // Helpers internes pour renvoyer des erreurs avec message personnalisé
    private ResponseEntity<String> forbidden(String message) {
        return ResponseEntity.status(403).body(message);
    }

    private ResponseEntity<String> bad(String message) {
        return ResponseEntity.badRequest().body(message);
    }
}
