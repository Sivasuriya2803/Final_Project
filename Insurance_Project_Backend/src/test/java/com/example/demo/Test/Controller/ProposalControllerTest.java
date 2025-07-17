package com.example.demo.Test.Controller;

import org.junit.jupiter.api.BeforeEach;
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
import com.example.demo.Controller.ProposalController;
import com.example.demo.Dto.ProposalRequest;
import com.example.demo.Dto.ProposalStatusDto;
import com.example.demo.Service.ProposalService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProposalController.class)
@AutoConfigureMockMvc(addFilters = false) 
class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProposalService proposalService;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // any setup if needed
    }

    @Test
    void submitProposal() throws Exception {
        ProposalRequest dto = new ProposalRequest();
        ProposalStatusDto statusDto = new ProposalStatusDto();

        Mockito.when(proposalService.submitProposal(any(ProposalRequest.class)))
                .thenReturn(ResponseEntity.ok(statusDto));

        mockMvc.perform(post("/api/proposals")
                        .with(csrf()) 
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getUserProposals() throws Exception {
        Mockito.when(proposalService.trackProposals(anyLong()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/api/proposals/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getQuoteGeneratedProposals() throws Exception {
        Mockito.when(proposalService.getQuoteGeneratedProposalsForUser(anyLong()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/proposals/quote-generated")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }
}