package com.kickoff.kickoff.dao;

import java.util.Optional;

import com.kickoff.kickoff.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Integer> { 
    
    public Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM User WHERE username = ?1 OR email = ?2" , nativeQuery = true)
    public Iterable<User> findUsername(String username, String email);

    @Query(value = "SELECT * FROM User WHERE role = ?1" , nativeQuery = true)
    public Iterable<User> getRole(String role);

	// public Optional<User> findEmail(String email);

	// @Query(value = "SELECT * FROM User WHERE email = ?1" , nativeQuery = true)
    // public Iterable<User> findEmail(String email);

	public Optional<User> findByVerifyToken(String token);

	public Optional<User> findByEmail(String email);
    
}