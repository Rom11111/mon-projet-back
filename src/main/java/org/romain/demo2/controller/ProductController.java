package org.romain.demo2.controller;

import jakarta.validation.Valid;
import org.romain.demo2.annotation.ValidFile;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.model.Etat;
import org.romain.demo2.model.Product;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;
import org.romain.demo2.security.IsAdmin;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.service.ServiceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class ProductController {

    protected ProductDao productDao;
    protected ISecurityUtils securityUtils;
    protected ServiceFile serviceFile;

    //@Autowired //Fait le lien avec la dépendence ProduitDao
    @Autowired
    public ProductController(ProductDao productDao, ISecurityUtils securityUtils, ServiceFile serviceFile) {
        this.productDao = productDao;
        this.securityUtils = securityUtils;
        this.serviceFile = serviceFile;
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
    @IsClient
    public List<Product> getAll() {

        return productDao.findAll();
    }

    @PostMapping("/product")
    @IsClient
    public ResponseEntity<Product> save(
            @RequestPart ("product") @Valid Product product,
            @RequestPart(value = "photo", required=false)
            @ValidFile(acceptedTypes = {"image/jpeg", "image/gif"}) MultipartFile photo,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        // Dans le cas d'un héritage
        product.setCreator(userDetails.getUser());

        // dans le cas d'un enum
        //product.setcreator(userDetails.getUser());


        if (product.getEtat() == null) {
            Etat newEtat = new Etat();
            newEtat.setId(1);
            product.setEtat(newEtat);

        }

        product.setId(null);

        if(photo != null) {
            try {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
                String imageName = date + "_" + product.getName() + "_" + UUID.randomUUID() + "_" + photo.getOriginalFilename();
                serviceFile.uploadToLocalFileSystem(photo, imageName, false);

                product.setImageName(imageName);

            }catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        productDao.save(product);

        product.setCreator((null));

        return new ResponseEntity<>(product, HttpStatus.CREATED);


    }

    @DeleteMapping("/product/{id}")
    @IsClient
    public ResponseEntity<Product> delete(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String role = securityUtils.getRole(userDetails);

        //si l'id du createur du produit est different de l'id de la personne connectée
        //et que la personne n'est pas l'admin, alors on envoie un erreur 403 FORBIDEN
        if (!role.equals("ROLE_ADMIN") &&
                optionalProduct.get().getCreator().getId() != userDetails.getUser().getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        productDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @PutMapping("/product/{id}") //mise à jour
    @IsClient
    public ResponseEntity<Product> update(
            @PathVariable int id,
            @RequestBody @Valid Product savingProduct) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //étant donné que le formulaire d'édition de produit ne permet pas de modifier
        // le vendeur du produit, on récupère l'ancien créateur et on le réaffecte au produit
        // à sauvegarder
        savingProduct.setCreator(optionalProduct.get().getCreator());

        savingProduct.setId(id);

        productDao.save(savingProduct);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/product/image/{idProduct}")
    @IsClient
    public ResponseEntity<byte[]> getImageProduct(@PathVariable int idProduct) {

        Optional<Product> optional = productDao.findById(idProduct);

        if (optional.isPresent()) {

            String imageName = optional.get().getImageName();

            try {
                byte[] image = serviceFile.getImageByName(imageName);

                HttpHeaders enTete = new HttpHeaders();
                String mimeType = Files.probeContentType(new File(imageName).toPath());
                enTete.setContentType(MediaType.valueOf(mimeType));

                return new ResponseEntity<>(image, enTete, HttpStatus.OK);

            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                System.out.println("Le test du mimetype a echoué");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
