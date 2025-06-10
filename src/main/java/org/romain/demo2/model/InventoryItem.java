//package org.romain.demo2.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//public class InventoryItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @NotNull
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "inventory_id")
//    private Inventory inventory;
//
//    @NotNull
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @Column(nullable = false)
//    private boolean present;
//
//    private String condition;
//
//    @Column(length = 1000)
//    private String notes;
//}