package com.springboot.miniproject.book.dto;


import jakarta.validation.constraints.NotBlank;

public class AuthRequestDTO {
	
	@NotBlank(message="userName cannot be blank")
	private String userName;
	@NotBlank(message="password cannot be blank")
	private String password;
	
	public AuthRequestDTO(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	

}
