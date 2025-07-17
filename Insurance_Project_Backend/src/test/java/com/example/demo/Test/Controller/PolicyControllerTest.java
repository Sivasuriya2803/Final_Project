package com.example.demo.Test.Controller;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Config.JwtTokenProvider;
import com.example.demo.Controller.PolicyController;
import com.example.demo.Dto.PolicyRequestDto;
import com.example.demo.Service.PolicyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PolicyController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PolicyService policyService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void getPolicies() throws Exception {
        Mockito.when(policyService.listPolicies())
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/api/policies"))
                .andExpect(status().isOk());
    }

    @Test
    void addPolicy() throws Exception {
        PolicyRequestDto dto = new PolicyRequestDto();
        Mockito.when(policyService.createPolicy(any(PolicyRequestDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/policies")
        		.with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePolicy() throws Exception {
        PolicyRequestDto dto = new PolicyRequestDto();
        Mockito.when(policyService.updatePolicy(anyLong(), any(PolicyRequestDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(put("/api/policies/1")
        		.with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePolicy() throws Exception {
        Mockito.when(policyService.deletePolicy(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/api/policies/1"))
        
                .andExpect(status().isOk());
    }
}
