package com.example.demo.Service;

import com.example.demo.Dto.PolicyRequestDto;
import com.example.demo.Entity.Policy;
import com.example.demo.Repository.PolicyRepository;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public ResponseEntity<?> createPolicy(PolicyRequestDto dto) {
        Policy policy = new Policy();
        policy.setName(dto.getName());
        policy.setDescription(dto.getDescription());
        policy.setBasePremium(dto.getBasePremium());
        policy.setAddOns(dto.getAddOns());
        policy.setIsActive(true);

        policyRepository.save(policy);
        return ResponseEntity.ok(Collections.singletonMap("message", "Policy Created successfully"));
    }

    public ResponseEntity<?> listPolicies() {
        return ResponseEntity.ok(policyRepository.findAll());
    }
    public ResponseEntity<?> updatePolicy(Long id, PolicyRequestDto dto) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
        }

        Policy policy = optionalPolicy.get();
        policy.setName(dto.getName());
        policy.setDescription(dto.getDescription());
        policy.setBasePremium(dto.getBasePremium());
        policy.setAddOns(dto.getAddOns());

        policyRepository.save(policy);
        return ResponseEntity.ok(Collections.singletonMap("message", "Policy updated successfully"));
    }

    public ResponseEntity<?> deletePolicy(Long id) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);
        if (optionalPolicy.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy not found");
        }

        policyRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Policy Deleted successfully"));
    }
}
