package com.springboot.miniproject.book.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;



@Configuration
public class UserDetailsConfig {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var user = User.builder()
            .username("nikitha")
            .password(encoder.encode("password123"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
