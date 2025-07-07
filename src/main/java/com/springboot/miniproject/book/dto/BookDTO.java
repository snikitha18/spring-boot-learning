package com.springboot.miniproject.book.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {
	@NotBlank(message = "Name cannot be empty")
	private String name;
	@NotBlank(message = "Author cannot be empty")
	private String author;
	public BookDTO(String name, String author) {   
	     this.name=name;
	     this.author=author;

	    
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
