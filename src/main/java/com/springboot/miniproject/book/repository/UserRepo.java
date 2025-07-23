package com.springboot.miniproject.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.miniproject.book.model.User;
@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	Optional<User> findByUserName(String username);
    boolean existsByUserName(String username);

}
