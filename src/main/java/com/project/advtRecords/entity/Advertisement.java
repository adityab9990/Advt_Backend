package com.project.advtRecords.entity;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String advtNo;



    private String advtName;
    private String department;
    
    @Enumerated(EnumType.STRING)
    private Category category; // Enum: PCMC, GOV

    private String size;
    private Double billRupees;
    private String pdfPath;
    private LocalDate publishingDate;
    private Boolean isPaid = false; // Add this field

    public enum Category { PCMC, GOV }


	}
