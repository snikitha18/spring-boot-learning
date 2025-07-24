package com.springboot.miniproject.book.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.miniproject.book.dto.LoginResponse;
import com.springboot.miniproject.book.dto.RegisterRequest;
import com.springboot.miniproject.book.model.AuthRequest;
import com.springboot.miniproject.book.service.AuthService;
import com.springboot.miniproject.book.utilities.JWTUtility;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired 
    private AuthenticationManager authManager;
    @Autowired 
    private JWTUtility jwtUtil;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest req) {
    	try {
            authManager.authenticate(
               new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
           
    		// 2. Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());

            // 3. Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            // 4. Return token
            return ResponseEntity.ok(new LoginResponse(token, "Login successful"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Invalid username or password"));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String message = authService.register(request);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
