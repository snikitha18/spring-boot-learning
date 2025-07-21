package com.springboot.miniproject.book.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	 @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(auth -> 
	                auth.anyRequest().authenticated()
	            )
	            .httpBasic(Customizer.withDefaults()); // Enables basic auth

	        return http.build();
	    }
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	     return authConfig.getAuthenticationManager();
	 }

	 @Bean
	 public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
	     UserDetails user = User.builder()
	         .username("nikitha")
	         .password(passwordEncoder.encode("password123"))
	         .roles("USER")
	         .build();
	     return new InMemoryUserDetailsManager(user);
	 }
	  @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); 
	    }

}
