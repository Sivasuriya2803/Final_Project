package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Proposal;
import com.example.demo.Entity.ProposalStatus;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByUserId(Long userId);
    List<Proposal> findByStatus(String status);
    List<Proposal> findByUserIdAndStatus(Long userId, ProposalStatus status);
}
