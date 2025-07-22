package com.springboot.miniproject.book.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.miniproject.book.filter.JwtFilter;
import com.springboot.miniproject.book.utilities.JWTUtility;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
    private JWTUtility jwtUtil;
	
	private UserDetailsService userDetailsService;

	@Autowired
	public void setUserDetailsService(@Lazy UserDetailsService uds) {
	    this.userDetailsService = uds;
	}
	
	 @Bean
	    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
		 JwtFilter jwtFilter = new JwtFilter(jwtUtil,userDetailsService);
		 return http
		            .csrf(csrf -> csrf.disable())                  
		            .authorizeHttpRequests(auth -> auth
		                .requestMatchers("/auth/**").permitAll()
		                .anyRequest().authenticated()
		            )
		            .sessionManagement(sm -> sm
		                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		            )
		            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		            .build();
		    
	    }
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	     return authConfig.getAuthenticationManager();
	 }

	
	  @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); 
	    }

}
