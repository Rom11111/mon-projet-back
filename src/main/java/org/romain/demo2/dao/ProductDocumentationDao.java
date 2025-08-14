package org.romain.demo2.dao;

import org.romain.demo2.model.ProductDocumentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDocumentationDao extends JpaRepository<ProductDocumentation, Long> {
    List<ProductDocumentation> findByProductIdOrderByCreatedAtDesc(Long productId);
}
