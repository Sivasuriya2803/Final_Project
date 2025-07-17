package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.ProposalRequest;
import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Service.ProposalService;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PostMapping
    public ResponseEntity<ProposalStatusDto> submitProposal(
            @RequestBody ProposalRequest proposalRequestDto) {
        return proposalService.submitProposal(proposalRequestDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserProposals(@PathVariable Long userId) {
        return proposalService.trackProposals(userId);
    }
    @GetMapping("/quote-generated")
    public ResponseEntity<List<ProposalStatusDto>> getQuoteGeneratedProposals(@RequestParam Long userId) {
        List<ProposalStatusDto> proposals = proposalService.getQuoteGeneratedProposalsForUser(userId);
        return ResponseEntity.ok(proposals);
    }
}
