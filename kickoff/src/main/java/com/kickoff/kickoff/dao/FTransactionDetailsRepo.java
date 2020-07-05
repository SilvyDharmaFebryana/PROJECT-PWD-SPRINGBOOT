package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.FTransactionDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FTransactionDetailsRepo extends JpaRepository<FTransactionDetails, Integer> {
    
    @Query(value = "SELECT * FROM ftransaction_details WHERE booking_date = ?1 AND time = ?2 AND field_id = ?3", nativeQuery = true)
    public Iterable<FTransactionDetails> findDateTimeField(String booking_date, String time, int field_id);

    @Query(value = "SELECT * FROM ftransaction_details WHERE field_transactions_id = ?1", nativeQuery = true)
    public Iterable<FTransactionDetails> findDetails(int field_transactions_id);


}