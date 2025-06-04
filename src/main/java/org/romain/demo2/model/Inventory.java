//package org.romain.demo2.model;
//
//import jakarta.persistence.*;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//public class Inventory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @NotNull
//    private LocalDate inventoryDate;
//
//    @ManyToOne
//    private User conductedBy;
//
//    private String notes;
//
//    @OneToMany(mappedBy = "inventory")
//    private List<InventoryItem> items;
//}

