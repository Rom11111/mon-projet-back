package org.romain.demo2.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO pour une demande de location de produit.
 */
@Data
public class RentalRequestDto {

    @NotNull(message = "L'ID du produit est requis")
    private Integer productId;

    @NotNull(message = "La date de début est requise")
    @Future(message = "La date de début doit être dans le futur")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est requise")
    @Future(message = "La date de fin doit être dans le futur")
    private LocalDate endDate;
}



