//package org.romain.demo2.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import java.time.LocalDateTime;
//
//@Entity
//public class RentalIssue {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @ManyToOne
//    private Rental rental;
//
//    @NotNull
//    private String description;
//
//    @NotNull
//    private LocalDateTime reportDate;
//
//    private IssueStatus status; // REPORTED, UNDER_INVESTIGATION, RESOLVED
//
//    private String resolution;
//
//    @ManyToOne
//    private User reportedBy;
//}