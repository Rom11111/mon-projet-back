package org.romain.demo2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.romain.demo2.dao.UserDao;
import org.romain.demo2.dto.UserCreationDTO;
import org.romain.demo2.model.Role;
import org.romain.demo2.model.User;
import org.romain.demo2.security.*;
import org.romain.demo2.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Utilisateur", description = "API de gestion des utilisateurs")
public class UserController {

    private final UserDao userDao;
    private final UserService userService;

    public UserController(UserDao userDao, UserService userService) {
        this.userDao = userDao;
        this.userService = userService;
    }

    /**
     * Récupère tous les utilisateurs avec pagination.
     * Accessible uniquement aux rôles TECH et ADMIN.
     */
    @GetMapping
    @IsTech
    @Operation(summary = "Lister tous les utilisateurs (paginer)", description = "Accessible aux techniciens et admins uniquement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste paginée des utilisateurs récupérée avec succès")
    })
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userDao.findAll(pageable);
    }

    /**
     * Récupère tous les clients (rôle CLIENT).
     * Accessible uniquement aux rôles TECH et ADMIN.
     */
    @GetMapping("/clients")
    @IsTech
    @Operation(summary = "Lister les clients", description = "Retourne les utilisateurs ayant le rôle CLIENT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des clients récupérée avec succès")
    })
    public List<User> getClients() {
        return userDao.findByRole(Role.CLIENT);
    }

    /**
     * Récupère un utilisateur par son ID.
     * Accessible uniquement aux TECH et ADMIN.
     */
    @GetMapping("/{id}")
    @IsTech
    @Operation(summary = "Récupérer un utilisateur par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<User> getById(@PathVariable int id) {
        return userDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retourne le profil de l'utilisateur actuellement connecté.
     *
     * Accessible à tous les rôles (ADMIN, TECH, CLIENT).
     * On récupère l'utilisateur à partir du token JWT.
     *
     * Si jamais l'utilisateur n'est pas bien chargé (problème de token ou de sécurité),
     * on retourne une erreur 500.
     */
    @GetMapping("/me")
    @Operation(summary = "Voir son propre profil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil utilisateur retourné"),
            @ApiResponse(responseCode = "500", description = "Erreur interne si utilisateur non trouvé dans le token")
    })
    @IsConnected
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof AppUserDetails userDetails)) {
            System.err.println("Erreur : utilisateur non reconnu dans le token");
            return ResponseEntity.status(500).build();
        }

        User currentUser = userDetails.getUser();
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Crée un nouvel utilisateur.
     * Accessible uniquement aux rôles TECH et ADMIN.
     */
    @PostMapping
    @IsTech
    @Operation(summary = "Créer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation ou données invalides")
    })
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationDTO dto) {
        User user = new User();

        user.setId(null); // Pour forcer la création
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setCompany(dto.getCompany());
        user.setCompanyAddress(dto.getCompanyAddress());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setUserStatus(dto.getUserStatus());

        User savedUser = userDao.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    /**
     * Met à jour un utilisateur existant par son ID.
     * Accessible uniquement aux rôles TECH et ADMIN.
     * Règles :
     * - Un TECH ne peut pas modifier un ADMIN
     * - Un TECH ne peut pas modifier un autre TECH
     */
    @PutMapping("/{id}")
    @IsTech
    @Operation(summary = "Mettre à jour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "403", description = "Interdit de modifier cet utilisateur"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        return userDao.findById(id)
                .map(existing -> {
                    // Interdiction pour TECH de modifier ADMIN ou un autre TECH
                    if (currentUser.getRole() == Role.TECH &&
                            (existing.getRole() == Role.ADMIN || existing.getRole() == Role.TECH)) {
                        return ResponseEntity.status(403).body("Un technicien ne peut pas modifier un administrateur ni un autre technicien");
                    }

                    updatedUser.setId(id);
                    User saved = userDao.save(updatedUser);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }




    /**
     * 🧹 Désactive un utilisateur (soft delete).
     * Accessible aux TECH et ADMIN.
     * Un TECH ne peut désactiver ni un ADMIN, ni un autre TECH.
     *
     * J'utilise ResponseEntity<?> ici parce que je peux avoir :
     * - un 204 (No Content) si tout se passe bien
     * - ou un 400 / 403 / 404 avec un message d'erreur dans le body
     * Du coup, le "?" me laisse le choix sans me prendre la tête avec les types.
     */
    @DeleteMapping("/{id}")
    @IsTech
    @Operation(summary = "Désactiver un utilisateur", description = "Désactive un utilisateur sauf si c'est un TECH ou ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur désactivé avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès interdit (rôle ou tentative illégale)"),
            @ApiResponse(responseCode = "400", description = "L'utilisateur est déjà inactif"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<?> deactivateUser(@PathVariable int id) {
        // Récupère l'objet Authentication de Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Cast vers ton UserDetails custom
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        // On récupère l'entité User directement
        User currentUser = userDetails.getUser();

        return userService.deactivateUser(currentUser, id);
    }

    /**
     * Réactive un utilisateur précédemment désactivé (soft deleted).
     * Accessible uniquement aux administrateurs (ADMIN).
     *
     * Règles de sécurité :
     * - Seul un ADMIN peut réactiver un utilisateur
     * - Impossible de réactiver un utilisateur déjà actif (retourne 400)
     * - Si l'utilisateur n'existe pas (ID inconnu), retourne 404
     *
     * Cette méthode appelle userService.reactivateUser(...) pour appliquer la logique métier.
     *
     * @param id l'identifiant de l'utilisateur à réactiver
     * @return 200 si réactivation OK, sinon 400, 403 ou 404 selon les cas
     */
    @PutMapping("/{id}/reactivate")
    @IsAdmin // Seul un administrateur peut réactiver un utilisateur
    @Operation(summary = "Réactiver un utilisateur désactivé", description = "Seul un ADMIN peut effectuer cette opération")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur réactivé avec succès"),
            @ApiResponse(responseCode = "403", description = "Interdit de réactiver cet utilisateur"),
            @ApiResponse(responseCode = "400", description = "L'utilisateur est déjà actif"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<?> reactivateUser(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        return userService.reactivateUser(currentUser, id);
    }

    /**
     * Supprime définitivement un utilisateur (hard delete).
     */
    @DeleteMapping("/{id}/hard")
    @IsAdmin
    @Operation(summary = "Supprimer définitivement un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé définitivement"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<Void> deleteUserPermanently(@PathVariable int id) {
        return userService.deleteUserPermanently(id);
    }
}
