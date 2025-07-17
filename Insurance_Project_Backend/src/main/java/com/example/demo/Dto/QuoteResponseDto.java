package com.example.demo.Dto;

import lombok.Data;

@Data
public class QuoteResponseDto {
    private Long quoteId;
    private Long proposalId;
    private Double premiumAmount;
    private String issuedAt;
}
