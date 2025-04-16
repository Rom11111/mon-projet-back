package org.romain.demo2.controller;


import jakarta.validation.Valid;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.model.Etat;
import org.romain.demo2.model.Product;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.IsAdmin;
import org.romain.demo2.security.IsUser;
import org.romain.demo2.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProductController {


    //@Autowired //Fait le lien avec la dépendence ProduitDao
    //protected DaoProduct daoProduct;

    protected ProductDao productDao;
    protected SecurityUtils securityUtils;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/product/{id}")
    @IsAdmin
    public ResponseEntity<Product> /* être plus precis sur le retour de la methode */ get(@PathVariable int id) {

        Optional<Product> productOptional = productDao.findById(id);

        if (productOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);

    }

    @GetMapping("/products")
    @IsUser
    public List<Product> getAll() {

        return productDao.findAll();
    }

    @PostMapping("/product")
    @IsUser
    public ResponseEntity<Product> save(
            @RequestBody @Valid Product product,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        product.setCreator(userDetails.getUser());


        if (product.getEtat() == null) {
            Etat newEtat = new Etat();
            newEtat.setId(1);
            product.setEtat(newEtat);

        }

        product.setId(null);
        productDao.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);


    }

    @DeleteMapping("/product/{id}")
    @IsUser
    public ResponseEntity<Product> delete(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String role = securityUtils.getRole(userDetails);

        //si l'id du createur du produit est different de l'id de la personne connectée
        if (!role.equals("ROLE_ADMIN") ||
                optionalProduct.get().getCreator().getId() != userDetails.getUser().getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        productDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/product/{id}") //mise à jour
    @IsUser
    public ResponseEntity<Product> update(
            @PathVariable int id,
            @RequestBody @Valid Product product) {

        Optional<Product> productOptional = productDao.findById(id);

        if (productOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setId(id);

        productDao.save(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
