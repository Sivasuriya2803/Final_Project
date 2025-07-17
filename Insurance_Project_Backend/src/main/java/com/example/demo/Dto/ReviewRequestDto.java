package com.example.demo.Dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private Long userId;
    private Integer rating;
    private String comment;
}
