package com.example.demo.Dto;

import lombok.Data;

@Data
public class PolicyRequestDto {
    private String name;
    private String description;
    private Double basePremium;
    private String addOns;
}
