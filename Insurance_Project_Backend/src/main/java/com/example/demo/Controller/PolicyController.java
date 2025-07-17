package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.PolicyRequestDto;
import com.example.demo.Entity.Policy;
import com.example.demo.Service.PolicyService;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping
    public ResponseEntity<?> getPolicies() {
        return policyService.listPolicies();
    }


    @PostMapping
    public ResponseEntity<?> addPolicy(@RequestBody PolicyRequestDto dto) {
        return policyService.createPolicy(dto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePolicy(@PathVariable Long id, @RequestBody PolicyRequestDto dto) {
        return policyService.updatePolicy(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePolicy(@PathVariable Long id) {
        return policyService.deletePolicy(id);
    }
    
}