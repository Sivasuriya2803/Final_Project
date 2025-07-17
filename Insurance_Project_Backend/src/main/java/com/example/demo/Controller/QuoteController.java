package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.QuoteResponseDto;
import com.example.demo.Service.QuoteService;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @PostMapping("/{proposalId}")
    public ResponseEntity<QuoteResponseDto> generateQuote(@PathVariable Long proposalId) {
        return quoteService.generateQuote(proposalId);
    }
}
