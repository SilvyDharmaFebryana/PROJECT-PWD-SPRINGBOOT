package com.kickoff.kickoff.dao;

import java.util.Optional;

import com.kickoff.kickoff.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> { 
    
    public Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM User WHERE username = ?1 OR email = ?2" , nativeQuery = true)
    public Iterable<User> findUsername(String username, String email);
    
}