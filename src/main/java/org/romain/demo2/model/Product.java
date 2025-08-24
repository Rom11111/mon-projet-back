package org.romain.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.romain.demo2.view.ProductViews;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProductViews.Client.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @JsonView(ProductViews.Client.class)
    private String name;

    @Size(min = 3, max = 40, message = "longueur entre 3 et 40")
    @NotBlank
    @Column(length = 15, nullable = false, unique = true)
    @JsonView(ProductViews.Tech.class)
    private String code;

    @Column(columnDefinition = "TEXT")
    @JsonView(ProductViews.Client.class)
    private String description;

    @DecimalMin("0.1")
    @JsonView(ProductViews.Client.class)
    private BigDecimal price;

    @ManyToOne
    @JsonView(ProductViews.Tech.class)
    private Etat etat;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonView(ProductViews.Admin.class)
    private User creator;

    // relation vers Category (ManyToOne)
    // fetch=EAGER pour recevoir la catégorie sans surprise
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonView(ProductViews.Client.class)
    private Category category;

    @JsonView(ProductViews.Client.class)
    private String imageName;

    @Column(nullable = false)
    @JsonView(ProductViews.Client.class)
    private boolean available = true;

    @NotNull
    @Min(0)
    @JsonView(ProductViews.Tech.class)
    private Integer stock;

    // 📄 Relation 1:N avec la documentation du produit
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    // pas de @JsonView ici si tu veux gérer l'affichage dans un endpoint séparé
    private List<ProductDocumentation> documentationList;
}
