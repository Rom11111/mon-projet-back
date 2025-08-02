package org.romain.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.romain.demo2.view.ProductViews;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProductViews.Client.class)
    protected Integer id;

    @Column(nullable = false)
    @NotBlank
    @JsonView(ProductViews.Client.class)
    protected String name;

    @Column(length = 15, nullable = false, unique = true)
    @Length(max = 40, min = 3, message = "longueur entre 3 et 10")
    @NotBlank
    @JsonView(ProductViews.Tech.class)
    protected String code;

    @Column(columnDefinition = "TEXT")
    @JsonView(ProductViews.Client.class)
    protected String description;

    @DecimalMin("0.1")
    @JsonView(ProductViews.Client.class)
    protected float price;

    @ManyToOne
    @JsonView(ProductViews.Tech.class)
    protected Etat etat;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonView(ProductViews.Admin.class)
    protected User creator;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonView(ProductViews.Tech.class)
    protected Category category;

    @JsonView(ProductViews.Client.class)
    protected String imageName;

    // champ pour activer/désactiver un produit
    @Column(nullable = false)
    @JsonView(ProductViews.Client.class) // visible par tous
    protected boolean available = true;

}