package com.example.demo.Dto;

import lombok.Data;

@Data
public class ClaimResponseDto {
    private Long claimId;
    private Long userId;
    private Long policyId;
    private String description;
    private String status;
    private String createdAt;
}
