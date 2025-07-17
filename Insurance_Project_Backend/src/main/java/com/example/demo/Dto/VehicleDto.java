package com.example.demo.Dto;

import com.example.demo.Entity.VehicleType;

import lombok.Data;

@Data
public class VehicleDto {
    private Long id;
    private Long userId;  // ✅ Add this!
    private VehicleType type;
    private String make;
    private String model;
    private Integer year;
    private String registrationNumber;
}

