package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.ProposalDocument;

import java.util.List;

public interface ProposalDocumentRepository extends JpaRepository<ProposalDocument, Long> {
    List<ProposalDocument> findByProposalId(Long proposalId);
}