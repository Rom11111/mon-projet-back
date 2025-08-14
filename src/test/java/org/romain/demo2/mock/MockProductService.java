package org.romain.demo2.mock;

import org.romain.demo2.model.Product;
import org.romain.demo2.service.ProductService;

class MockProductService extends ProductService {

    public MockProductService() {
        super(null); // on passe null car on ne teste pas le DAO ici
    }

    @Override
    public Product toggleAvailability(Long id) {
        Product p = new Product();
        p.setId(id);
        p.setAvailable(true); // valeur fictive pour ton test
        return p;
    }
}
