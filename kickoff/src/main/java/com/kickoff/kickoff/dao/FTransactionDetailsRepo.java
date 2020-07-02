package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.FTransactionDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FTransactionDetailsRepo extends JpaRepository<FTransactionDetails, Integer> {
    
}