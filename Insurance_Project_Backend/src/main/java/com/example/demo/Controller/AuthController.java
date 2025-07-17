package com.example.demo.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Config.JwtTokenProvider;
import com.example.demo.Dto.ApiResponse;
import com.example.demo.Dto.LoginRequest;
import com.example.demo.Dto.RegisterRequest;
import com.example.demo.Dto.UserProfileDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthController(
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponse("Invalid username or password", false));
        }

        String token = jwtTokenProvider.generateToken(user); 

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId());

        return ResponseEntity.ok(response);
    }

@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    return authService.registerUser(request);
}



@PostMapping("/register/officer")
public ResponseEntity<?> registerOfficer(@RequestBody RegisterRequest request) {
    return authService.registerOfficer(request);
}

@GetMapping("/profile")
public ResponseEntity<UserProfileDto> getProfile() {
    return ResponseEntity.ok(authService.getCurrentUserProfile());
}
}

