package org.romain.demo2.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Un modèle structure les données
@Getter // annotation
@Setter
@Entity
public class Label {

    @Id // Preciser quelle propriété a une clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Stratégie à appliquer
    protected Integer id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String color;


}
