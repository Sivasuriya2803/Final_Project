package com.example.demo.Dto;
import lombok.Data;

@Data
public class ClaimRequestDto {
    private Long userId;
    private Long policyId;
    private String description;
}
