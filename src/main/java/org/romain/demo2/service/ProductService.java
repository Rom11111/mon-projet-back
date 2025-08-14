package org.romain.demo2.service;

import lombok.RequiredArgsConstructor;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.exception.ResourceNotFoundException;
import org.romain.demo2.model.Product;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public Product toggleAvailability(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));

        product.setAvailable(!product.isAvailable());

        return productDao.save(product);
    }

    public Product updateStock(Long productId, int newStock) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));

        product.setStock(newStock);

        return productDao.save(product);
    }

    // méthode pour récupérer un produit par son ID
    public Product getProductById(Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));
    }

}

