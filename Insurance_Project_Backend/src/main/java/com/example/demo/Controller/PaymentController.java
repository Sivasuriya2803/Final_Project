package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.PaymentRequest;
import com.example.demo.Dto.PaymentResponseDto;
import com.example.demo.Service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> makePayment(
            @RequestBody PaymentRequest paymentRequestDto) {
        return paymentService.completePayment(paymentRequestDto);
    }
    

    @GetMapping("/premium-amount")
    public ResponseEntity<Double> getPremiumAmount(@RequestParam Long proposalId) {
        double amount = paymentService.getPremiumAmountByProposal(proposalId);
        return ResponseEntity.ok(amount);
    }

    @PostMapping("/complete")
    public ResponseEntity<PaymentResponseDto> completePayment(@RequestBody PaymentRequest request) {
        return paymentService.completePayment(request);
    }
    
    @GetMapping("/completed")
    public ResponseEntity<List<PaymentResponseDto>> getAllCompletedPayments() {
        List<PaymentResponseDto> payments = paymentService.getAllCompletedPayments();
        return ResponseEntity.ok(payments);
    }

    
    
}
