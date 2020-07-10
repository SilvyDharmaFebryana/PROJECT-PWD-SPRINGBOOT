package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.FieldTransactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FieldTransactionsRepo extends JpaRepository<FieldTransactions, Integer> {
    
    @Query(value = "SELECT * FROM field_transactions WHERE status = ?1", nativeQuery = true)
    public Iterable<FieldTransactions> status(String status);

    @Query(value = "SELECT * FROM field_transactions WHERE attempt = ?1", nativeQuery = true)
    public Iterable<FieldTransactions> getAttempt(int attempt);
}