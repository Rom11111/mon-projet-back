package org.romain.demo2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.romain.demo2.dto.ApiResponseDto;
import org.romain.demo2.dto.RentalResponseDto;
import org.romain.demo2.dto.RentalRequestDto;
import org.romain.demo2.model.Rental;
import org.romain.demo2.model.RentalStatus;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
import org.romain.demo2.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.romain.demo2.dto.ReportProductRequestDto;
import org.romain.demo2.model.Report;

import java.util.List;
import java.util.Map;
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
     * Liste toutes les locations accessibles selon le rôle de l'utilisateur connecté :
     * - CLIENT : ne voit que ses propres locations
     * - TECH / ADMIN : voit toutes les locations
     */
    @GetMapping
    @Operation(summary = "Lister les locations")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    public ResponseEntity<ApiResponseDto<List<RentalResponseDto>>> getAll(
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        var user = userDetails.getUser(); // utilisateur connecté

        // Récupère les locations accessibles en fonction du rôle
        List<Rental> rentals = rentalService.findAllAccessibleBy(user);

        // Conversion entités -> DTO
        List<RentalResponseDto> rentalDtos = rentals.stream()
                .map(RentalResponseDto::from)
                .toList();

        return ResponseEntity.ok(new ApiResponseDto<>("Liste des locations récupérée.", rentalDtos));
    }

    /**
     * Récupère uniquement les locations de l'utilisateur connecté (CLIENT).
     */
    @GetMapping("/my")
    @IsClient
    @Operation(summary = "Lister mes propres locations (client uniquement)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès interdit")
    })
    public ResponseEntity<ApiResponseDto<List<RentalResponseDto>>> getMyRentals(
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        var client = userDetails.getUser(); // récupère le client depuis le token

        // Récupère uniquement ses locations
        List<Rental> rentals = rentalService.findAllAccessibleBy(client);

        // Conversion en DTO
        List<RentalResponseDto> rentalDtos = rentals.stream()
                .map(RentalResponseDto::from)
                .toList();

        return ResponseEntity.ok(new ApiResponseDto<>("Vos locations ont bien été récupérées.", rentalDtos));
    }

    /**
     * Récupère une location par son ID si l'utilisateur a le droit de la voir.
     */
    @GetMapping("/{rentalId}")
    @Operation(summary = "Voir une location par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    public ResponseEntity<ApiResponseDto<RentalResponseDto>> getById(
            @PathVariable("rentalId") Long rentalId,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return rentalService.findById(rentalId)
                .filter(rental -> rentalService.canAccessRental(userDetails.getUser(), rental))
                .map(rental -> ResponseEntity.ok(
                        new ApiResponseDto<>("Location trouvée.", RentalResponseDto.from(rental))
                ))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new ApiResponseDto<>("Location introuvable ou accès interdit.", null))
                );
    }

    /**
     * Crée une nouvelle location (réservé aux clients).
     */
    @PostMapping("/create")
    @IsClient
    @Operation(summary = "Créer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Location créée"),
            @ApiResponse(responseCode = "409", description = "Produit déjà réservé")
    })
    public ResponseEntity<ApiResponseDto<RentalResponseDto>> create(
            @RequestBody @Valid RentalRequestDto dto,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        try {
            // Création de la location pour ce client
            Rental saved = rentalService.createRental(dto, userDetails.getUser().getId());

            return ResponseEntity.status(201)
                    .body(new ApiResponseDto<>("La location a bien été créée.", RentalResponseDto.from(saved)));
        } catch (IllegalStateException e) {
            // En cas de stock déjà réservé
            return ResponseEntity.status(409)
                    .body(new ApiResponseDto<>(e.getMessage(), null));
        }
    }

    /**
     * Met à jour une location existante (réservé aux TECH ou ADMIN).
     */
    @PutMapping("/{rentalId}")
    @IsTech
    @Operation(summary = "Mettre à jour une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location mise à jour"),
            @ApiResponse(responseCode = "403", description = "Interdit"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    public ResponseEntity<ApiResponseDto<RentalResponseDto>> update(
            @PathVariable("rentalId") Long rentalId,
            @RequestBody @Valid RentalRequestDto dto,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return rentalService.updateRentalWithResult(rentalId, userDetails.getUser(), dto)
                .map(rental -> ResponseEntity.ok(
                        new ApiResponseDto<>("La location a bien été mise à jour.", RentalResponseDto.from(rental))
                ))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(new ApiResponseDto<>("Location introuvable ou accès interdit.", null))
                );
    }

    /**
     * Met à jour uniquement le statut d'une location (TECH ou ADMIN).
     */
    @PutMapping("/{rentalId}/status")
    @IsTech
    @Operation(summary = "Changer le statut d'une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Statut mis à jour"),
            @ApiResponse(responseCode = "404", description = "Location introuvable")
    })
    public ResponseEntity<ApiResponseDto<RentalResponseDto>> updateRentalStatus(
            @PathVariable Long rentalId,
            @RequestBody Map<String, String> body
    ) {
        String newStatus = body.get("status"); // statut envoyé par le front
        Rental updatedRental = rentalService.updateStatus(rentalId, RentalStatus.valueOf(newStatus));

        return ResponseEntity.ok(
                new ApiResponseDto<>("Statut mis à jour", RentalResponseDto.from(updatedRental))
        );
    }

    /**
     * Supprime une location (TECH ou ADMIN).
     */
    @DeleteMapping("/{rentalId}")
    @IsTech
    @Operation(summary = "Supprimer une location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Supprimée avec succès"),
            @ApiResponse(responseCode = "403", description = "Non autorisé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(
            @PathVariable("rentalId") Long rentalId,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return rentalService.deleteRental(rentalId, userDetails.getUser())
                ? ResponseEntity.ok(new ApiResponseDto<>("La location a bien été supprimée.", null))
                : ResponseEntity.status(403)
                .body(new ApiResponseDto<>("Suppression non autorisée ou location introuvable.", null));
    }

    /**
     * Permet à un client de signaler un problème sur une location qu'il possède.
     */
    @PostMapping("/{rentalId}/report")
    @IsClient
    @Operation(summary = "Signaler un problème sur un produit loué")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Signalement enregistré"),
            @ApiResponse(responseCode = "403", description = "Non autorisé"),
            @ApiResponse(responseCode = "404", description = "Location introuvable")
    })
    public ResponseEntity<ApiResponseDto<Long>> reportProduct(
            @PathVariable Long rentalId,
            @Valid @RequestBody ReportProductRequestDto request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        var currentUser = userDetails.getUser();

        Report report = rentalService.reportProduct(
                rentalId,
                currentUser.getId(),
                request.getDescription()
        );

        return ResponseEntity.ok(
                new ApiResponseDto<>("Signalement enregistré", report.getId())
        );
    }


}

