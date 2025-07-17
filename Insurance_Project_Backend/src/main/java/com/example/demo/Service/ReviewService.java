package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ApiResponse;
import com.example.demo.Dto.ReviewRequestDto;
import com.example.demo.Entity.Review;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.ReivewMapper;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReivewMapper reviewMapper;

    public ResponseEntity<?> submitReview(Long userId, ReviewRequestDto reviewRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewMapper.toEntity(reviewRequestDto);
        review.setUser(user);
        reviewRepository.save(review);

        return ResponseEntity.ok(new ApiResponse("Review submitted successfully", true));
    }
}
