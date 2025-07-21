package com.springboot.miniproject.book.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.miniproject.book.model.AuthRequest;
import com.springboot.miniproject.book.utilities.JWTUtility;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired 
    private AuthenticationManager authManager;
    @Autowired 
    private JWTUtility jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody AuthRequest req) {
        authManager.authenticate(
           new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String token = jwtUtil.generateToken(req.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
