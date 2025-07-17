package com.example.demo.Test.Controller;


import com.example.demo.Config.JwtTokenProvider;
import com.example.demo.Controller.AuthController;
import com.example.demo.Dto.LoginRequest;
import com.example.demo.Dto.RegisterRequest;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_success() throws Exception {
        User user = new User();
        user.setUsername("john");
        user.setPassword("encodedPass");
        user.setId(1L);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john");
        loginRequest.setPassword("password");

        Mockito.when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);
        Mockito.when(jwtTokenProvider.generateToken(any(User.class)))
                .thenReturn("mockToken");

        mockMvc.perform(post("/api/auth/login")
        		         .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    void registerUser() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test");
        request.setPassword("pass");

        Mockito.when(authService.registerUser(any(RegisterRequest.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/auth/register")
        		.with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void registerOfficer() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("officer");
        request.setPassword("pass");

        Mockito.when(authService.registerOfficer(any(RegisterRequest.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/auth/register/officer")
        		.with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getProfile() throws Exception {
        Mockito.when(authService.getCurrentUserProfile())
                .thenReturn(null); 

        mockMvc.perform(get("/api/auth/profile"))
                .andExpect(status().isOk());
    }
}