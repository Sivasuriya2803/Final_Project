package com.example.demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ApiResponse;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public ResponseEntity<?> sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sivasuriyaitskct@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
        return ResponseEntity.ok(new ApiResponse("Email sent successfully", true));
    }
}
