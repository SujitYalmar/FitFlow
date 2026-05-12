
package com.becoder.service;

import com.becoder.entity.User;
import com.becoder.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("No user found with the given email");
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpirationDate(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        sendResetEmail(user.getEmail(), token);
    }

    private void sendResetEmail(String email, String token) {
        String resetUrl = "http://localhost:8089/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the following link: " + resetUrl);

        mailSender.send(message);
    }

    public void resetPassword(String email, String newPassword) {
    	//System.out.println("my password is"+token);
        //User user = userRepository.findByResetToken(token);
            User user=userRepository.findByEmail(email);
        if (user == null || user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
        
        
    }
}
