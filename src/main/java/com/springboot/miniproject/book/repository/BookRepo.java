package com.springboot.miniproject.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.miniproject.book.dto.BookDTO;
import com.springboot.miniproject.book.model.Books;

@Repository
public interface BookRepo extends JpaRepository<Books,Long>{
	
	

}
