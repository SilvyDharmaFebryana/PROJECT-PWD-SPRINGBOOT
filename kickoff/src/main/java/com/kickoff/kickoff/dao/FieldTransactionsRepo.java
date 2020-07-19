package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.FieldTransactions;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FieldTransactionsRepo extends JpaRepository<FieldTransactions, Integer>, PagingAndSortingRepository<FieldTransactions, Integer> {
    
    @Query(value = "SELECT * FROM field_transactions WHERE status = ?1 AND user_id= ?2", nativeQuery = true)
    public Iterable<FieldTransactions> status(String status, int user_id);

    @Query(value = "SELECT * FROM field_transactions WHERE attempt = ?1", nativeQuery = true)
    public Iterable<FieldTransactions> getAttempt(int attempt);

    @Query(value = "SELECT * FROM field_transactions WHERE status = ?1", nativeQuery = true)
    public Iterable<FieldTransactions> statusForAdmin(String status);

    public Page<FieldTransactions> findAll(Pageable pageable);
    
    

   
}