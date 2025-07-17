package com.example.demo.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Service.OfficerService;

@RestController
@RequestMapping("/api/officer")
public class OfficerController {

    @Autowired
    private OfficerService officerService;

    @PutMapping("/review/{proposalId}")
    public ResponseEntity<?> reviewProposal(
            @PathVariable Long proposalId,
            @RequestParam boolean approve) {
        officerService.reviewProposal(proposalId, approve);
        return ResponseEntity.ok(Collections.singletonMap("message", "Proposal reviewed successfully"));
    }
    @GetMapping("/proposals")
    public ResponseEntity<List<ProposalStatusDto>> getAllProposals() {
        return ResponseEntity.ok(officerService.getAllProposals());
    }
    
    
    
    }
