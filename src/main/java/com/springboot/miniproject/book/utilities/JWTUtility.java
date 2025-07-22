package com.springboot.miniproject.book.utilities;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JWTUtility {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
    	 try {
    	        System.out.println("Decoded secret: " + secret);
    	        byte[] keyBytes = Decoders.BASE64.decode(secret);
    	        System.out.println("Decoded key length: " + keyBytes.length);  // Should be at least 32 bytes
    	        key = Keys.hmacShaKeyFor(keyBytes);
    	    } catch (Exception e) {
    	        System.err.println("ERROR during JWTUtility init: " + e.getMessage());
    	        e.printStackTrace();  // This will print the exact cause
    	    }
    }

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + jwtExpirationMs))
            .signWith(key)  // uses HS256 by default
            .compact();
    }
    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

	public String extractUsername(String token) {
		 return extractAllClaims(token).getSubject();
	}

	public boolean validateToken(String token, UserDetails user) {
		 try {
		        Claims claims = extractAllClaims(token);
		        String username = claims.getSubject();
		        Date expiration = claims.getExpiration();
		        return username.equals(user.getUsername()) && expiration.after(new Date());
		    } 
		 catch (JwtException | IllegalArgumentException e) {
		        return false;  // invalid, tampered, or expired token
		    }
	
	}
}
