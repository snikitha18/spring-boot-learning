package com.springboot.miniproject.book.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
	@NotBlank(message = "UserName cannot be empty")
	private String username;
	@NotBlank(message = "Password cannot be empty")
	private String password;
	
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}

