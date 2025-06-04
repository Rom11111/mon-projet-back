//package org.romain.demo2.model;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDate;
//
//@Entity
//public class Maintenance {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    private Equipment equipment;
//
//    @NotNull
//    private LocalDate maintenanceDate;
//
//    private String description;
//
//    private String technicianName;
//
//    private MaintenanceType type; // PREVENTIVE, CORRECTIVE
//
//    private String result; // OK, NEEDS_REPLACEMENT, etc.
//}