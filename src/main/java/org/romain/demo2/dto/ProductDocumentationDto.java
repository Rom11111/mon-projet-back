package org.romain.demo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDocumentationDto {
    private Long id;
    private String type;
    private String title;
    private String storageKey;
    private String originalFilename;
    private String contentType;
    private Long sizeBytes;
}
