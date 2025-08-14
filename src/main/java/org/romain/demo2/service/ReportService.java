package org.romain.demo2.service;

import org.romain.demo2.dao.ReportDao;

import org.romain.demo2.dto.ReportDto;
import org.romain.demo2.exception.BusinessException;
import org.romain.demo2.model.Report;
import org.romain.demo2.model.ReportStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les signalements (reports).
 */
@Service
public class ReportService {

    private final ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    /**
     * Récupère tous les signalements faits par un client donné.
     *
     * @param clientId ID du client connecté
     * @return liste des signalements sous forme de DTO
     */
    public List<ReportDto> getReportsByClient(Long clientId) {
        // On va chercher dans la base tous les reports faits par ce client
        List<Report> reports = reportDao.findByReportedById(clientId);

        // Conversion en DTO pour ne pas exposer l'entité directement
        return reports.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ReportDto> getAllReports() {
        return reportDao.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Convertit un Report en ReportDto.
     */
    private ReportDto mapToDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setRentalId(report.getRental().getId());
        dto.setProductName(report.getRental().getProduct().getName());
        dto.setDescription(report.getDescription());
        dto.setStatus(report.getStatus());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setResolvedAt(report.getResolvedAt());
        return dto;
    }

    public ReportDto updateReportStatus(Long reportId, ReportStatus newStatus) {
        Report report = reportDao.findById(reportId)
                .orElseThrow(() -> new BusinessException("Signalement introuvable"));

        report.setStatus(newStatus);

        if (newStatus == ReportStatus.RESOLVED) {
            report.setResolvedAt(LocalDateTime.now());
        } else {
            report.setResolvedAt(null);
        }

        reportDao.save(report);
        return mapToDto(report);
    }

}

