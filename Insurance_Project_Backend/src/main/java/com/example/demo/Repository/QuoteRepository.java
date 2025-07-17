package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Quote;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Optional<Quote> findByProposalId(Long proposalId);
}