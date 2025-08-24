
package org.romain.demo2.dao;

import org.romain.demo2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    // Ici je n'ai rien ajouté car JpaRepository fournit déjà les méthodes de base :
    // - save(product) → pour créer ou modifier un produit
    // - findById(id) → pour récupérer un produit par son ID
    // - findAll() → pour lister tous les produits
    // - deleteById(id) → pour supprimer un produit
}
