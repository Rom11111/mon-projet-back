package org.romain.demo2.dao;

import org.romain.demo2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Met en place le systeme de query method et ...
public interface ProductDao extends JpaRepository<Product, Integer> {


}
