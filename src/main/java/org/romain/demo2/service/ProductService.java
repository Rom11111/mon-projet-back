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

    public Product toggleAvailability(Integer id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé"));

        product.setAvailable(!product.isAvailable());

        return productDao.save(product);
    }
}

