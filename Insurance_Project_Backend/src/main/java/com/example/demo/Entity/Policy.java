package com.example.demo.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double basePremium;

    @Column(columnDefinition = "TEXT")
    private String addOns;

    private Boolean isActive = true;
}
