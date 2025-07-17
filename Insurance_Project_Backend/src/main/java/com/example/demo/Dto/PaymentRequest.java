package com.example.demo.Dto;


import lombok.Data;

@Data
public class PaymentRequest {
    private Long proposalId;
    private Double paymentAmount;
}
