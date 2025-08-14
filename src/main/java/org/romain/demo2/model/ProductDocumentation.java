package org.romain.demo2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "product_documentation")
public class ProductDocumentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lien vers le produit (plusieurs assets par produit)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    // Type fonctionnel de l’asset (notice, doc technique, vidéo, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DocumentationType type;

    // Titre lisible côté UI (ex: "Notice FR v1.2")
    @Column(nullable = false)
    private String title;

    // Méta fichier (pour servir/filtrer)
    private String originalFilename;
    private String contentType;
    private Long sizeBytes;

    // Clé technique de stockage (chemin ou clé S3)
    @Column(nullable = false, unique = true)
    private String storageKey;

    private Instant createdAt = Instant.now();

}
