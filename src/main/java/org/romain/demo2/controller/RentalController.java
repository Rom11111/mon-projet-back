package org.romain.demo2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.dto.RentalDTO;
import org.romain.demo2.model.Rental;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
import org.romain.demo2.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Location", description = "API de gestion des locations de produits")
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {

    private final RentalDao rentalDao;
    private final ISecurityUtils securityUtils;
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalDao rentalDao, ISecurityUtils securityUtils, RentalService rentalService) {
        this.rentalDao = rentalDao;
        this.securityUtils = securityUtils;
        this.rentalService = rentalService;
    }

    /**
     * Liste toutes les locations.
     * - ADMIN et TECH voient tout
     * - CLIENT voit seulement ses locations
     */
    @GetMapping
    @Operation(summary = "Lister les locations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    public ResponseEntity<List<Rental>> getAll(@AuthenticationPrincipal AppUserDetails userDetails) {
        List<Rental> rentals = rentalService.findAllAccessibleBy(userDetails.getUser());
        return ResponseEntity.ok(rentals);
    }


    /**
     * Récupère une location spécifique par son ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Voir une location par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    public ResponseEntity<?> getById(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Rental> optional = rentalService.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Location introuvable.");
        }

        Rental rental = optional.get();
        if (!rentalService.canAccessRental(userDetails.getUser(), rental)) {
            return ResponseEntity.status(403).body("Vous n'avez pas accès à cette location.");
        }

        return ResponseEntity.ok(rental);
    }

    /**
     * Crée une nouvelle location.
     * - Uniquement les CLIENTS peuvent louer
     */
    @PostMapping
    @IsClient
    @Operation(summary = "Créer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Location créée"),
            @ApiResponse(responseCode = "409", description = "Produit déjà réservé")
    })
    public ResponseEntity<?> create(@RequestBody @Valid RentalDTO dto, @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            Rental saved = rentalService.createRental(userDetails.getUser(), dto);
            return ResponseEntity.status(201).body(saved);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    /**
     * Met à jour une location (ADMIN ou TECH propriétaire).
     */
    @PutMapping("/{id}")
    @IsTech
    @Operation(summary = "Mettre à jour une location")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Location mise à jour"),
            @ApiResponse(responseCode = "403", description = "Interdit"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestBody @Valid RentalDTO dto,
                                    @AuthenticationPrincipal AppUserDetails userDetails) {
        return rentalService.updateRental(id, userDetails.getUser(), dto);
    }

    /**
     * Supprime une location.
     * - ADMIN peut tout supprimer
     * - TECH peut supprimer ses propres locations
     */
    @DeleteMapping("/{id}")
    @IsTech
    @Operation(summary = "Supprimer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimée avec succès"),
            @ApiResponse(responseCode = "403", description = "Non autorisé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        return rentalService.deleteRental(id, userDetails.getUser());
    }
}
