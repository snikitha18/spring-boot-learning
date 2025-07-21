package com.springboot.miniproject.book.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RequestMapping("/")
public class bookController {
	@Autowired
	BookService service;
	@GetMapping("books")
	public ResponseEntity<Page<BookDTO>> getBooks(
		 @RequestParam(defaultValue = "0") int page,
		 @RequestParam(defaultValue = "5") int size,
		 @RequestParam(defaultValue = "id") String sortBy,
		 @RequestParam(required = false) String author,

		 @RequestParam(defaultValue = "asc") String sortDir
		) {
		    Sort sort = sortDir.equalsIgnoreCase("asc")
		        ? Sort.by(sortBy).ascending()
		        : Sort.by(sortBy).descending();

		    Pageable pageable = PageRequest.of(page, size, sort);
	
		
		return ResponseEntity.status(HttpStatus.CREATED).body( service.getAllBooks(pageable));
		

	}
	/*
	 * @PostMapping("/login") public ResponseEntity<Map<String, String>>
	 * login(@RequestParam() String username, @RequestParam() String password) {
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body(
	 * service.login(username,password)); }
	 */
	
	@GetMapping("/info")
	public ResponseEntity<Map<String, String>> getTitle() {
	    return ResponseEntity.status(HttpStatus.OK).body( service.getInfo());
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
	public ResponseEntity<Void> deleteBooks(@PathVariable@Min(1) long id) {
		service.deleteBook(id);
		return ResponseEntity.noContent().build();
	}


}
