package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.QuoteResponseDto;
import com.example.demo.Entity.Proposal;
import com.example.demo.Entity.ProposalStatus;
import com.example.demo.Entity.VehicleType;
import com.example.demo.Repository.ProposalRepository;

import java.time.LocalDateTime;
@Service
public class QuoteService {

    @Autowired
    private ProposalRepository proposalRepository;

    public ResponseEntity<QuoteResponseDto> generateQuote(Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        VehicleType vehicleType = proposal.getVehicleType();

        double premium = calculatePremium(vehicleType);

        proposal.setStatus(ProposalStatus.QUOTE_GENERATED);
        proposal.setQuoteGeneratedAt(LocalDateTime.now());
        proposalRepository.save(proposal);

        QuoteResponseDto response = new QuoteResponseDto();
        response.setQuoteId(generateQuoteId());
        response.setProposalId(proposalId);
        response.setPremiumAmount(premium);
        response.setIssuedAt(LocalDateTime.now().toString());

        return ResponseEntity.ok(response);
    }

    public double calculatePremium(VehicleType vehicleType) {
        double baseRate = 2000.0;

        double typeFactor = getVehicleTypeFactor(vehicleType);
        double riskFactor = getRiskFactor();

        return baseRate * typeFactor * riskFactor;
    }

    private double getVehicleTypeFactor(VehicleType vehicleType) {
        switch (vehicleType) {
            case CAR: return 1.0;
            case TRUCK: return 1.4;
            case MOTORCYCLE: return 0.8;
            case CAMPER_VAN: return 1.6;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }

    private double getRiskFactor() {
        return 1.1;
    }

    private Long generateQuoteId() {
        return System.currentTimeMillis();
    }
}

   

