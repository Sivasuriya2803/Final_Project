package com.example.demo.Dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PaymentResponseDto {
	@Column(name = "id")
    private Long paymentId;
    private Long proposalId;
    private Double paymentAmount;
    private String paymentDate;
    private String paymentStatus;
}