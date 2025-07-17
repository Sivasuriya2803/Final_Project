package com.example.demo.Dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String address;
    private String dob; 
    private String aadhaarNumber;
    private String panNumber;
}