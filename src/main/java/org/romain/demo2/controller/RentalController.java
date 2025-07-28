package org.romain.demo2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.romain.demo2.dto.ApiResponseDto;
import org.romain.demo2.dto.RentalRequestDto;
import org.romain.demo2.model.Rental;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;

import org.romain.demo2.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Location", description = "API de gestion des locations de produits")
@CrossOrigin(origins = "http://localhost:4200")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * Liste toutes les locations accessibles selon le rôle :
     * - CLIENT : ses propres locations
     * - TECH / ADMIN : toutes
     */
    @GetMapping
    @Operation(summary = "Lister les locations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    public ResponseEntity<?> getAll(@AuthenticationPrincipal AppUserDetails userDetails) {
        List<Rental> rentals = rentalService.findAllAccessibleBy(userDetails.getUser());
        return ResponseEntity.ok(new ApiResponseDto<>("Liste des locations récupérée.", rentals));
    }


    /**
     * Récupère une location par son ID si l'utilisateur a le droit.
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
            return ResponseEntity.status(404).body(new ApiResponseDto<>("Location introuvable.", null));
        }

        Rental rental = optional.get();
        if (!rentalService.canAccessRental(userDetails.getUser(), rental)) {
            return ResponseEntity.status(403).body(new ApiResponseDto<>("Vous n'avez pas accès à cette location.", null));
        }

        return ResponseEntity.ok(new ApiResponseDto<>("Location trouvée.", rental));
    }

    /**
     * Crée une nouvelle location (réservé aux clients).
     */
    @PostMapping
    @IsClient
    @Operation(summary = "Créer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Location créée"),
            @ApiResponse(responseCode = "409", description = "Produit déjà réservé")
    })
    public ResponseEntity<?> create(@RequestBody @Valid RentalRequestDto dto,
                                    @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            Rental saved = rentalService.createRental(dto, userDetails.getUser().getId());
            return ResponseEntity.status(201).body(new ApiResponseDto<>("La location a bien été créée.", saved));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(new ApiResponseDto<>(e.getMessage(), null));
        }
    }

    /**
     * Met à jour une location existante (réservé au TECH ou ADMIN).
     */
    @PutMapping("/{id}")
    @IsTech
    @Operation(summary = "Mettre à jour une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location mise à jour"),
            @ApiResponse(responseCode = "403", description = "Interdit"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestBody @Valid RentalRequestDto dto,
                                    @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Rental> updated = rentalService.updateRentalWithResult(id, userDetails.getUser(), dto);

        if (updated.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponseDto<>("Location introuvable ou accès interdit.", null));
        }

        return ResponseEntity.ok(new ApiResponseDto<>("La location a bien été mise à jour.", updated.get()));
    }

    /**
     * Supprime une location (ADMIN ou TECH propriétaire).
     */
    @DeleteMapping("/{id}")
    @IsTech
    @Operation(summary = "Supprimer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Supprimée avec succès"),
            @ApiResponse(responseCode = "403", description = "Non autorisé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public ResponseEntity<?> delete(@PathVariable int id,
                                    @AuthenticationPrincipal AppUserDetails userDetails) {
        boolean deleted = rentalService.deleteRental(id, userDetails.getUser());

        if (!deleted) {
            return ResponseEntity.status(403).body(new ApiResponseDto<>("Suppression non autorisée ou location introuvable.", null));
        }

        return ResponseEntity.ok(new ApiResponseDto<>("La location a bien été supprimée.", null));
    }
}
