package com.springboot.miniproject.book.exception;

public class BookNotFoundException extends RuntimeException{
	public BookNotFoundException(String message) {
		super(message);
	}

}
