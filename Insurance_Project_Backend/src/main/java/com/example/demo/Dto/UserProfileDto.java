package com.example.demo.Dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String address;
    private String dob;
    private String aadhaarNumber;
    private String panNumber;
}