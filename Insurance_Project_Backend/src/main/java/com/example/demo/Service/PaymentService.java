package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.PaymentRequest;
import com.example.demo.Dto.PaymentResponseDto;
import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Dto.VehicleDto;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.PaymentStatus;
import com.example.demo.Entity.Proposal;
import com.example.demo.Entity.ProposalStatus;
import com.example.demo.Entity.Quote;
import com.example.demo.Mapper.PaymentMapper;
import com.example.demo.Mapper.ProposalMapper;
import com.example.demo.Mapper.VehicleMapper;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.Repository.ProposalRepository;
import com.example.demo.Repository.QuoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PaymentService {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProposalMapper proposalMapper;  
    @Autowired
    private VehicleMapper vehicleMapper;    


    public ResponseEntity<PaymentResponseDto> completePayment(PaymentRequest dto) {
        Proposal proposal = proposalRepository.findById(dto.getProposalId())
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        if (proposal.getStatus() == ProposalStatus.REJECTED) {
            throw new RuntimeException("Cannot make payment. The proposal has been rejected.");
        }

        Payment payment = new Payment();
        payment.setProposal(proposal);
        payment.setPaymentAmount(dto.getPaymentAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        Payment savedPayment = paymentRepository.save(payment);

        proposal.setStatus(ProposalStatus.ACTIVE);
        proposalRepository.save(proposal);

        ProposalStatusDto proposalStatusDto = proposalMapper.toStatusDto(proposal);

        String vehicleDetails = "N/A";
        if (proposal.getVehicleType() != null) {
            vehicleDetails = "Vehicle Type: " + proposal.getVehicleType().name();
        }
        String to = proposal.getUser().getEmail();
        String subject = "Payment Confirmation - Thank You!";
        String text = "Dear " + proposal.getUser().getName() + ",\n\n"
                + "Thank you for your payment of ₹" + dto.getPaymentAmount() + ".\n\n"
                + "Your policy is now active.\n\n"
                + "=== Policy Details ===\n"
                + "Proposal ID: " + proposalStatusDto.getProposalId() + "\n"
                + "User ID: " + proposalStatusDto.getUserId() + "\n"
                + "Status: " + proposal.getStatus() + "\n"
                + "Submitted At: " + proposalStatusDto.getSubmittedAt() + "\n"
                + "Quote Generated At: " + proposalStatusDto.getQuoteGeneratedAt() + "\n\n"
                + "=== Vehicle Details ===\n"
                + vehicleDetails + "\n\n"
                + "We appreciate your trust!\n\nRegards,\nInsurance Team";

        emailService.sendSimpleEmail(to, subject, text);

        PaymentResponseDto responseDto = paymentMapper.toDto(savedPayment);
        return ResponseEntity.ok(responseDto);
    }


    public double getPremiumAmountByProposal(Long proposalId) {
        Quote quote = quoteRepository.findByProposalId(proposalId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));
        return quote.getPremiumAmount();
    }
    public List<PaymentResponseDto> getAllCompletedPayments() {
        List<Payment> completedPayments = paymentRepository.findByPaymentStatus(PaymentStatus.COMPLETED);

        return completedPayments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

}



