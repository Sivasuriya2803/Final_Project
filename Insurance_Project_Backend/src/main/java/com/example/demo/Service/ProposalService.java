package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ProposalRequest;
import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Entity.Policy;
import com.example.demo.Entity.Proposal;
import com.example.demo.Entity.ProposalStatus;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Vehicle;
import com.example.demo.Mapper.ProposalMapper;
import com.example.demo.Repository.PolicyRepository;
import com.example.demo.Repository.ProposalRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.VehicleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    
    
    @Autowired private ProposalMapper proposalMapper;

    public ResponseEntity<ProposalStatusDto> submitProposal(ProposalRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Policy policy = policyRepository.findById(request.getPolicyId())
            .orElseThrow(() -> new RuntimeException("Policy not found"));

        Proposal proposal = new Proposal();
        proposal.setUser(user);
        proposal.setPolicyId(policy.getId());
        proposal.setVehicleType(request.getVehicleType()); 
        proposal.setStatus(ProposalStatus.PROPOSAL_SUBMITTED);
        proposal.setSubmittedAt(LocalDateTime.now());

        proposalRepository.save(proposal);

        ProposalStatusDto statusDto = new ProposalStatusDto();
        statusDto.setProposalId(proposal.getId());
        statusDto.setUserId(user.getId());
        statusDto.setStatus(proposal.getStatus().name());
        statusDto.setSubmittedAt(proposal.getSubmittedAt().toString());
        statusDto.setQuoteGeneratedAt(null);

        return ResponseEntity.ok(statusDto);
    }

    public ResponseEntity<?> trackProposals(Long userId) {
        List<Proposal> proposals = proposalRepository.findByUserId(userId);

        List<ProposalStatusDto> proposalDtos = proposals.stream().map(proposal -> {
            ProposalStatusDto dto = new ProposalStatusDto();
            dto.setProposalId(proposal.getId());
            dto.setStatus(proposal.getStatus().name());
            dto.setSubmittedAt(proposal.getSubmittedAt().toString());
            if (proposal.getQuoteGeneratedAt() != null) {
                dto.setQuoteGeneratedAt(proposal.getQuoteGeneratedAt().toString());
            } else {
                dto.setQuoteGeneratedAt(null);
            }
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(proposalDtos);
    }
    
    public List<ProposalStatusDto> getQuoteGeneratedProposalsForUser(Long userId) {
        List<Proposal> proposals = proposalRepository.findByUserIdAndStatus(userId, ProposalStatus.QUOTE_GENERATED);
        return proposals.stream()
                .map(proposalMapper::toStatusDto)
                .collect(Collectors.toList());
    }

}
