package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Entity.Proposal;
import com.example.demo.Entity.ProposalStatus;
import com.example.demo.Entity.Quote;
import com.example.demo.Entity.VehicleType;
import com.example.demo.Mapper.ProposalMapper;
import com.example.demo.Repository.ProposalRepository;
import com.example.demo.Repository.QuoteRepository;

import jakarta.transaction.Transactional;
@Service
public class OfficerService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalMapper proposalMapper;
    

    @Autowired
    private QuoteRepository quoteRepository;


    public List<ProposalStatusDto> getAllProposals() {
        List<Proposal> proposals = proposalRepository.findAll();
        return proposals.stream()
                .map(proposalMapper::toStatusDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<?> reviewProposal(Long proposalId, boolean approve) {
        Proposal proposal = proposalRepository.findById(proposalId)
            .orElseThrow(() -> new RuntimeException("Proposal not found"));

        if (approve) {
            proposal.setStatus(ProposalStatus.QUOTE_GENERATED);
            proposal.setQuoteGeneratedAt(LocalDateTime.now());
            proposalRepository.save(proposal);

            // ✅ Call the quote generation logic
            generateQuote(proposal);
        } else {
            proposal.setStatus(ProposalStatus.REJECTED);
            proposalRepository.save(proposal);
        }

        return ResponseEntity.ok(Map.of(
            "message", "Proposal " + proposalId + " reviewed successfully"
        ));
    }
    public void generateQuote(Proposal proposal) {
        double premium = calculatePremium(proposal.getVehicleType());

        Quote quote = new Quote();
        quote.setProposal(proposal);
        quote.setPremiumAmount(premium);
        quote.setIssuedAt(LocalDateTime.now());

        quoteRepository.save(quote);
    }
    
    private double calculatePremium(VehicleType vehicleType) {
        double baseRate = 2000.0;

        switch (vehicleType) {
            case CAR: return baseRate * 1.0;
            case TRUCK: return baseRate * 1.5;
            case MOTORCYCLE: return baseRate * 0.8;
            case CAMPER_VAN: return baseRate * 1.2;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }




  
}

