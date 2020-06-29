package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.Pool;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PoolRepo extends JpaRepository<Pool, Integer> {
    
}