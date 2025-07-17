package com.example.demo.Entity;
import com.example.demo.Entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id; 

	    @ManyToOne
	    @JoinColumn(name = "proposal_id")
	    private Proposal proposal;

	    private Double paymentAmount;

	    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
}
