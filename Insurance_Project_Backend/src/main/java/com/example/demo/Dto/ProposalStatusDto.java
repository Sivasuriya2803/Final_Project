package com.example.demo.Dto;


import lombok.Data;

@Data
public class ProposalStatusDto {
    private Long proposalId;
    private Long userId;
    private String status;
    private String submittedAt;
    private String quoteGeneratedAt;
}

