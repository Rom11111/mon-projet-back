package org.romain.demo2.dao;

import org.romain.demo2.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO pour gérer l'accès aux signalements dans la base de données.
 */
@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    List<Report> findByReportedById(Long clientId);
}
