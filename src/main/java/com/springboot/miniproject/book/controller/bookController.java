package com.springboot.miniproject.book.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.miniproject.book.dto.BookDTO;
import com.springboot.miniproject.book.model.Books;
import com.springboot.miniproject.book.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/")
public class bookController {
	@Autowired
	BookService service;
	@GetMapping("books")
	public ResponseEntity<List<BookDTO>> getBooks() {
		return ResponseEntity.status(HttpStatus.CREATED).body( service.getAllBooks());
	}
	@PostMapping("books")
	public ResponseEntity<BookDTO> addBooks(@Valid@RequestBody BookDTO book) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addBook(book));
		
	}
	@PutMapping("books/{id}")
	public ResponseEntity<BookDTO> updateBooks(@PathVariable@Min(1) long id,@Valid@RequestBody BookDTO book) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateBook(id, book));
		
	}
	@DeleteMapping("books/{id}")
	public ResponseEntity<Boolean> deleteBooks(@PathVariable@Min(1) long id) {
		return ResponseEntity.status(HttpStatus.GONE).body(service.deleteBook(id));
	}


}
