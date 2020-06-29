package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.Field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FieldRepo extends JpaRepository<Field, Integer> {
    
    @Query(value = "SELECT * FROM Field WHERE category = ?1", nativeQuery = true)
    public Iterable<Field> findField(String category);

}