package com.example.demo.Dto;

import com.example.demo.Entity.VehicleType;

import lombok.Data;

@Data
public class ProposalRequest {
	 private Long userId;
	    private Long policyId;
	    private VehicleType vehicleType;
}