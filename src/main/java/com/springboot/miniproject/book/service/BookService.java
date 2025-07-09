package com.springboot.miniproject.book.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.springboot.miniproject.book.configuration.Config;
import com.springboot.miniproject.book.dto.BookDTO;
import com.springboot.miniproject.book.exception.BookNotFoundException;
import com.springboot.miniproject.book.model.Books;
import com.springboot.miniproject.book.repository.BookRepo;

import jakarta.annotation.PostConstruct;

@Service
@Profile("dev")
public class BookService {

	
	@Autowired
	BookRepo repo;
	@PostConstruct
	public void debugProfile() {
	    System.out.println("Active Profile is: " + System.getProperty("spring.profiles.active"));
	    System.out.println("Config bean: " + config);
	}
	
	public Page<BookDTO> getAllBooks(Pageable pageable){
		Page<Books> bookPage=repo.findAll(pageable);
		Page<BookDTO> bkdtoList = bookPage.map(this::convertToDTO);
		/*
		 * for(Books bk:bkPage) { bkdtoList.add(convertToDTO(bk)); }
		 */
		return bkdtoList;
	}
	public BookDTO addBook(BookDTO book){
		Books bk = convertToEntity(book);
	    Books saved = repo.save(bk);
	    return convertToDTO(bk);
	}
	private BookDTO convertToDTO(Books book) {
		
		 return new BookDTO(book.getName(),book.getAuthor());
	}
	private Books convertToEntity(BookDTO book) {		
		return new Books(book.getName(),book.getAuthor());
	}
	public BookDTO updateBook(long id,BookDTO book){
		
			Optional<Books> b = repo.findById(id);	
			if(b.isPresent()) {
				Books a = b.get();
				a.setName(book.getName());
				a.setAuthor(book.getAuthor());
				repo.save(a);
				return convertToDTO(a);
			}
			Books saved = repo.save(convertToEntity(book));	
			return convertToDTO(saved);	
			
			
				
			
			
		
				
	}
	public void deleteBook(long id)  throws BookNotFoundException {
		Optional<Books> b = repo.findById(id);	
		if(b.isPresent()) {
			Books a = b.get();
			 repo.delete(a);
		}
		else
		throw new BookNotFoundException("Requested book of id "+id+" doesnot exist in DB");
	
	}
	@Autowired
	Config config;
	public Map<String,String> getInfo() {
		Map<String,String> info = new HashMap<>();
		info.put("ProjectTitle", config.getTitle());
		info.put("ProjectVersion", config.getVersion());
		info.put("ProjectAuthor", config.getAuthor());
		return info;
	}
}
