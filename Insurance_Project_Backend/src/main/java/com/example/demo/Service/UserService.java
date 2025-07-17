package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ApiResponse;
import com.example.demo.Dto.UserProfileDto;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<UserProfileDto> getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDto dto = userMapper.toUserProfileDto(user);
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> updateProfile(Long userId, UserProfileDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse("Profile updated successfully", true));
    }
}