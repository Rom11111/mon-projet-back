package org.romain.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.romain.demo2.dto.ApiResponseDto;
import org.romain.demo2.dto.ProductDocumentationDto;
import org.romain.demo2.model.DocumentationType;
import org.romain.demo2.model.Product;
import org.romain.demo2.model.ProductDocumentation;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
import org.romain.demo2.service.FileStorageService;
import org.romain.demo2.service.ProductDocumentationService;
import org.romain.demo2.service.ProductService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/documentation")
public class ProductDocumentationController {

    private final ProductDocumentationService documentationService;
    private final ProductService productService;
    private final FileStorageService fileStorageService;

    // --- LISTE DES DOCUMENTS D’UN PRODUIT ---
    @GetMapping
    @IsClient
    public ResponseEntity<ApiResponseDto<List<ProductDocumentationDto>>> listDocumentation(@PathVariable Long productId) {
        List<ProductDocumentationDto> docs = documentationService.getDocumentationDtosForProduct(productId);
        return ResponseEntity.ok(new ApiResponseDto<>("Liste des documents récupérée avec succès.", docs));
    }

    // --- TÉLÉCHARGER UN DOCUMENT ---
    @GetMapping("/{docId}/download")
    @IsClient
    public ResponseEntity<Resource> downloadDocumentation(
            @PathVariable Long productId,
            @PathVariable Long docId,
            @RequestParam(name = "download", defaultValue = "false") boolean download
    ) {
        ProductDocumentation doc = documentationService.getDocumentationById(docId);
        Resource file = fileStorageService.loadFileAsResource(doc.getStorageKey());

        String contentType = doc.getContentType();
        if (contentType == null || !contentType.contains("/")) {
            contentType = "application/octet-stream";
        }

        String dispositionType = download ? "attachment" : "inline";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, dispositionType + "; filename=\"" + doc.getOriginalFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

    // --- AJOUT DES MÉTADONNÉES SANS UPLOAD ---
    @PostMapping
    @IsTech
    public ResponseEntity<ApiResponseDto<Void>> addDocumentation(
            @PathVariable Long productId,
            @RequestParam DocumentationType type,
            @RequestParam String title,
            @RequestParam String storageKey,
            @RequestParam(required = false) String originalFilename,
            @RequestParam(required = false) String contentType,
            @RequestParam(required = false) Long sizeBytes
    ) {
        Product product = productService.getProductById(productId);
        documentationService.addDocumentation(product, type, title, storageKey, originalFilename, contentType, sizeBytes);
        return ResponseEntity.ok(new ApiResponseDto<>("Document ajouté avec succès !", null));
    }

    // --- UPLOAD DIRECT DU FICHIER AVEC MÉTADONNÉES ---
    @PostMapping("/upload")
    @IsTech
    public ResponseEntity<ApiResponseDto<Void>> uploadDocumentation(
            @PathVariable Long productId,
            @RequestParam DocumentationType type,
            @RequestParam String title,
            @RequestParam MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Aucun fichier envoyé");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.contains("/")) {
            contentType = "application/octet-stream";
        }

        Product product = productService.getProductById(productId);
        String storageKey = fileStorageService.saveFile(file, "documentation");

        documentationService.addDocumentation(
                product,
                type,
                title,
                storageKey,
                file.getOriginalFilename(),
                contentType,
                file.getSize()
        );

        return ResponseEntity.ok(new ApiResponseDto<>("Document uploadé avec succès !", null));
    }

    // --- SUPPRESSION D’UN DOCUMENT ---
    @DeleteMapping("/{docId}")
    @IsTech
    public ResponseEntity<ApiResponseDto<Void>> deleteDocumentation(
            @PathVariable Long productId,  // 👈 requis par la route de classe
            @PathVariable Long docId
    ) {
        documentationService.deleteDocumentation(docId);
        return ResponseEntity.ok(new ApiResponseDto<>("Document supprimé avec succès !", null));
    }
}
