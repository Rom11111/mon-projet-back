package org.romain.demo2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportProductRequestDto {
    @NotBlank(message = "La description est obligatoire")
    private String description;
}