package org.romain.demo2.service;

import lombok.RequiredArgsConstructor;
import org.romain.demo2.dao.ProductDocumentationDao;
import org.romain.demo2.dto.ProductDocumentationDto;
import org.romain.demo2.exception.ResourceNotFoundException;
import org.romain.demo2.model.DocumentationType;
import org.romain.demo2.model.Product;
import org.romain.demo2.model.ProductDocumentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok → génère le constructeur avec mes dépendances
public class ProductDocumentationService {

    // DAO pour communiquer avec la base sur la table product_documentation
    private final ProductDocumentationDao documentationDao;

    /**
     * 🔹 Retourne la liste des documents (ENTITÉS) liés à un produit,
     * triés par date de création décroissante.
     * ⚠️ Cette méthode renvoie les entités JPA complètes → à éviter directement en JSON
     * sinon risque de réponses énormes.
     */
    public List<ProductDocumentation> getDocumentationForProduct(Long productId) {
        return documentationDao.findByProductIdOrderByCreatedAtDesc(productId);
    }

    /**
     * 🔹 Retourne la liste des documents sous forme de DTO,
     * prêts à être renvoyés au client dans une réponse GET.
     * Avantage : plus léger, aucun risque de boucle JSON, format maîtrisé.
     */
    public List<ProductDocumentationDto> getDocumentationDtosForProduct(Long productId) {
        return getDocumentationForProduct(productId).stream()
                .map(doc -> new ProductDocumentationDto(
                        doc.getId(),
                        doc.getType().name(),
                        doc.getTitle(),
                        doc.getStorageKey(),
                        doc.getOriginalFilename(),
                        doc.getContentType(),
                        doc.getSizeBytes()
                ))
                .toList();
    }

    /**
     * 🔹 Récupère un document par son ID (ENTITÉ complète).
     * Sert pour les opérations qui ont besoin de toutes les métadonnées :
     * téléchargement, modification, suppression.
     * Si l’ID n’existe pas → je lève une exception 404 métier.
     */
    public ProductDocumentation getDocumentationById(Long docId) {
        return documentationDao.findById(docId)
                .orElseThrow(() -> new ResourceNotFoundException("Document introuvable"));
    }

    /**
     * 🔹 Ajoute un document en base avec toutes ses métadonnées.
     * Utilisé soit après upload du fichier, soit pour enregistrer un fichier déjà existant ailleurs.
     */
    public void addDocumentation(Product product, DocumentationType type, String title,
                                 String storageKey, String originalFilename,
                                 String contentType, Long sizeBytes) {
        ProductDocumentation doc = new ProductDocumentation();
        doc.setProduct(product);
        doc.setType(type);
        doc.setTitle(title);
        doc.setStorageKey(storageKey);
        doc.setOriginalFilename(originalFilename);
        doc.setContentType(contentType);
        doc.setSizeBytes(sizeBytes);
        documentationDao.save(doc);
    }

    /**
     * Supprime un document en base.
     * Ici je ne supprime pas le fichier physique, juste la ligne en base.
     * (Si besoin, je pourrais aussi appeler le FileStorageService pour supprimer le fichier réel)
     */
    public void deleteDocumentation(Long docId) {
        documentationDao.delete(
                documentationDao.findById(docId)
                        .orElseThrow(() -> new ResourceNotFoundException("Document introuvable"))
        );
    }
}
