package org.romain.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.romain.demo2.view.ProductViews;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProductViews.Client.class)
    private Long id;

    @Column(nullable = false, length = 255)
    @JsonView(ProductViews.Client.class)
    private String name;
}
