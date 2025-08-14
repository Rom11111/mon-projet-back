package org.romain.demo2.controller;

import org.romain.demo2.dto.ApiResponseDto;

import org.romain.demo2.dto.ReportDto;
import org.romain.demo2.model.ReportStatus;
import org.romain.demo2.security.AppUserDetails;

import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
import org.romain.demo2.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour gérer les signalements (reports)
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/my-reports")
    @IsClient
    @Operation(summary = "Lister mes signalements")
    public ResponseEntity<ApiResponseDto<List<ReportDto>>> getMyReports(
            @AuthenticationPrincipal AppUserDetails userDetails) {

        var currentUser = userDetails.getUser();
        List<ReportDto> reports = reportService.getReportsByClient(currentUser.getId());

        return ResponseEntity.ok(
                new ApiResponseDto<>("Liste des signalements récupérée", reports)
        );
    }

    @GetMapping
    @IsTech
    @Operation(summary = "Lister tous les signalements")
    public ResponseEntity<ApiResponseDto<List<ReportDto>>> getAllReports() {

        List<ReportDto> reports = reportService.getAllReports();

        return ResponseEntity.ok(
                new ApiResponseDto<>("Liste de tous les signalements récupérée", reports)
        );
    }

    @PatchMapping("/{reportId}/status")
    @IsTech
    @Operation(summary = "Mettre à jour le statut d'un signalement")
    public ResponseEntity<ApiResponseDto<ReportDto>> updateReportStatus(
            @PathVariable Long reportId,
            @RequestParam ReportStatus status) {

        ReportDto updated = reportService.updateReportStatus(reportId, status);

        return ResponseEntity.ok(
                new ApiResponseDto<>("Statut du signalement mis à jour", updated)
        );
    }
}


