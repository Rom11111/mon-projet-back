package org.romain.demo2.dto;

import lombok.Getter;
import lombok.Setter;
import org.romain.demo2.model.ReportStatus;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDto {
    private Long id;
    private Long rentalId;
    private String productName;
    private String description;
    private ReportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}


