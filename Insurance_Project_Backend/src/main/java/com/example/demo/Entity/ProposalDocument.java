package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "proposal_documents")
public class ProposalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Proposal proposal;

    private String documentName;
    private String documentUrl;
    private LocalDateTime uploadedAt;
}