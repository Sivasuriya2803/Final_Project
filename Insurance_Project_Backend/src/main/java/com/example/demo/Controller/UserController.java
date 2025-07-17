package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.ClaimRequestDto;
import com.example.demo.Dto.ClaimResponseDto;
import com.example.demo.Dto.ReviewRequestDto;
import com.example.demo.Dto.UserProfileDto;
import com.example.demo.Service.ClaimService;
import com.example.demo.Service.ReviewService;
import com.example.demo.Service.UserService;
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ClaimService claimService;

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable Long userId) {
        return userService.getProfile(userId);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileDto userDto) {
        return userService.updateProfile(userId, userDto);
    }

    @PostMapping("/{userId}/review")
    public ResponseEntity<?> submitReview(
            @PathVariable Long userId,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.submitReview(userId, reviewRequestDto);
    }

    @PostMapping("/{userId}/claims")
    public ResponseEntity<ClaimResponseDto> fileClaim(
            @PathVariable Long userId,
            @RequestBody ClaimRequestDto claimRequestDto) {
        return claimService.fileClaim(userId, claimRequestDto);
    }

    @GetMapping("/{userId}/claims")
    public ResponseEntity<?> getClaims(@PathVariable Long userId) {
        return claimService.getUserClaims(userId);
    }
}