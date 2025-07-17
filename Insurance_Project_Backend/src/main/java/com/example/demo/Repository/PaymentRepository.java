package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Payment;
import com.example.demo.Entity.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByProposalId(Long proposalId);
    List<Payment> findByPaymentStatus(PaymentStatus status);
}