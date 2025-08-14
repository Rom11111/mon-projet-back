package org.romain.demo2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.romain.demo2.annotation.ValidFile;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.dto.ApiResponseDto;
import org.romain.demo2.model.Etat;
import org.romain.demo2.model.Product;
import org.romain.demo2.security.*;
import org.romain.demo2.service.ProductService;
import org.romain.demo2.service.ServiceFile;
import org.romain.demo2.view.ProductViews;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")  //Ajout du préfixe commun
public class ProductController {

    protected ProductDao productDao;
    protected ISecurityUtils securityUtils;
    protected ServiceFile serviceFile;
    protected ProductService productService;

    //@Autowired //Fait le lien avec la dépendence ProduitDao
    @Autowired
    public ProductController(ProductDao productDao, ISecurityUtils securityUtils, ServiceFile serviceFile, ProductService productService) {
        this.productDao = productDao;
        this.securityUtils = securityUtils;
        this.serviceFile = serviceFile;
        this.productService = productService;
    }

    @GetMapping("/admin/products")
    @IsTech
    @JsonView(ProductViews.Tech.class)
    public List<Product> getAllAsTech() {
        return productDao.findAll();
    }

    @GetMapping("/product/{id}")
    @IsClient
    @JsonView(ProductViews.Client.class)
    public ResponseEntity<Product> /* être plus precis sur le retour de la methode */ get(@PathVariable Long id) {

        Optional<Product> productOptional = productDao.findById(id);

        if (productOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/products")
    @IsClient
    @JsonView(ProductViews.Client.class)
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
            newEtat.setId(1L);
            product.setEtat(newEtat);
        }

        product.setId(null);

        if(photo != null) {
            try {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
                String imageName = date + "_" + product.getName() + "_" + UUID.randomUUID() + "_" + photo.getOriginalFilename();
                serviceFile.uploadToLocalFileSystem(photo, imageName, true);

                // Sauvegarde le nom de l'image dans le produit
                product.setImageName(imageName);

            }catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        productDao.save(product);

        // Supprime le créateur pour éviter de l'envoyer côté client
        product.setCreator((null));

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
    @IsTech
    @Operation(summary = "Supprimer un produit")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit supprimé"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Produit introuvable")
    })
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponseDto<>("Produit introuvable", null));
        }

        String role = securityUtils.getRole(userDetails);

        if (!role.equals("ROLE_ADMIN") &&
                !optionalProduct.get().getCreator().getId().equals(userDetails.getUser().getId())) {
            return ResponseEntity.status(403)
                    .body(new ApiResponseDto<>("Suppression non autorisée", null));
        }

        productDao.deleteById(id);

        return ResponseEntity.ok(new ApiResponseDto<>("Produit supprimé avec succès", null));
    }



    @PutMapping("/product/{id}")
    @IsClient
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestPart("product") @Valid Product savingProduct,
            @RequestPart(value = "photo", required = false)
            @ValidFile(acceptedTypes = {"image/jpeg", "image/png"}) MultipartFile photo
    ) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        savingProduct.setCreator(optionalProduct.get().getCreator());
        savingProduct.setId(id);

        // Si une nouvelle image est envoyée
        if (photo != null && !photo.isEmpty()) {
            try {
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
                String imageName = date + "_" + savingProduct.getName() + "_" + UUID.randomUUID() + "_" + photo.getOriginalFilename();
                serviceFile.uploadToLocalFileSystem(photo, imageName, true);
                savingProduct.setImageName(imageName);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Conserver l'ancienne image
            savingProduct.setImageName(optionalProduct.get().getImageName());
        }

        productDao.save(savingProduct);
        return new ResponseEntity<>(savingProduct, HttpStatus.OK);
    }


    @PutMapping("/admin/product/{id}/stock")
    @IsTech
    @Operation(
            summary = "Modifier le stock d’un produit",
            description = "Permet aux techniciens et admins de modifier la quantité en stock."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock mis à jour"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    public ResponseEntity<ApiResponseDto<Product>> updateStock(@PathVariable Long id,
                                                               @RequestBody Map<String, Integer> body) {
        Integer stock = body.get("stock");

        if (stock == null) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>("Stock manquant dans le corps de la requête", null));
        }

        Product updatedProduct = productService.updateStock(id, stock);

        return ResponseEntity.ok(new ApiResponseDto<>("Stock mis à jour avec succès", updatedProduct));
    }


    @GetMapping("/product/image/{idProduct}")
    @IsClient
    public ResponseEntity<byte[]> getImageProduct(@PathVariable Long idProduct) {

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

    @PatchMapping("/admin/product/{id}/toggle-availability")
    @IsTech
    @Operation(
            summary = "Activer ou désactiver un produit",
            description = "Inverse la disponibilité d’un produit. Accessible uniquement aux admins."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilité mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Produit introuvable"),
            @ApiResponse(responseCode = "403", description = "Accès interdit (non admin)")
    })
    public ResponseEntity<ApiResponseDto<Product>> toggleAvailability(@PathVariable Long id) {
        Product updatedProduct = productService.toggleAvailability(id);
        return ResponseEntity.ok(
                new ApiResponseDto<>("Disponibilité mise à jour", updatedProduct)
        );
    }
}
