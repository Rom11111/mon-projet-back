package org.romain.demo2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.romain.demo2.dao.UserDao;
import org.romain.demo2.model.Role;
import org.romain.demo2.model.User;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.IsAdmin;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
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
     * Accessible à tous les rôles connectés.
     */
    @GetMapping("/{id}")
    @IsClient
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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(null); // On force à null pour éviter les conflits avec un ID déjà existant
        User savedUser = userDao.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    /**
     * Met à jour un utilisateur existant par son ID.
     * Accessible uniquement aux rôles TECH et ADMIN.
     */
    @PutMapping("/{id}")
    @IsTech
    @Operation(summary = "Mettre à jour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userDao.findById(id)
                .map(existing -> {
                    updatedUser.setId(id); // On force l'ID pour s'assurer qu'on écrase le bon enregistrement
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
