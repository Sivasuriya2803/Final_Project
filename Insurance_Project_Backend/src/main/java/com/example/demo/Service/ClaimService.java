package com.example.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ClaimRequestDto;
import com.example.demo.Dto.ClaimResponseDto;
import com.example.demo.Entity.Claim;
import com.example.demo.Entity.ClaimStatus;
import com.example.demo.Entity.Policy;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.ClaimMapper;
import com.example.demo.Repository.ClaimRepository;
import com.example.demo.Repository.PolicyRepository;
import com.example.demo.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ClaimMapper claimMapper;

    public ResponseEntity<ClaimResponseDto> fileClaim(Long userId, ClaimRequestDto claimRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Policy policy = policyRepository.findById(claimRequestDto.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        Claim claim = claimMapper.toEntity(claimRequestDto);
        claim.setUser(user);
        claim.setPolicy(policy);
        claim.setStatus(ClaimStatus.APPROVED);
        claim.setCreatedAt(LocalDateTime.now());
        claimRepository.save(claim);

        ClaimResponseDto dto = claimMapper.toDto(claim);
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> getUserClaims(Long userId) {
        List<Claim> claims = claimRepository.findByUserId(userId);
        List<ClaimResponseDto> dtos = claims.stream()
                .map(claimMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}

