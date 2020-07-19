package com.kickoff.kickoff.dao;

import java.util.List;

import com.kickoff.kickoff.entity.FTransactionDetails;
import com.kickoff.kickoff.entity.FieldTransactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FTransactionDetailsRepo extends JpaRepository<FTransactionDetails, Integer>, PagingAndSortingRepository<FTransactionDetails, Integer>{
    
    @Query(value = "SELECT * FROM ftransaction_details WHERE booking_date = ?1 AND time = ?2 AND field_id = ?3", nativeQuery = true)
    public Iterable<FTransactionDetails> findDateTimeField(String booking_date, String time, int field_id);

    @Query(value = "SELECT * FROM ftransaction_details WHERE field_transactions_id = ?1", nativeQuery = true)
    public Iterable<FTransactionDetails> findDetails(FieldTransactions findFieldTransactions);

    @Query(
        value = "SELECT * FROM ftransaction_details fd INNER JOIN field_transactions ft on fd.field_transactions_id = ft.id WHERE ft.status = ?1 AND fd.field_id = ?2", 
        nativeQuery = true)
    public List<FTransactionDetails> reportDay(String status, int field_id);

    @Query(
        value = "SELECT * FROM ftransaction_details fd INNER JOIN field_transactions ft on fd.field_transactions_id = ft.id WHERE ft.status = ?1 AND fd.field_id = ?2 AND fd.booking_date = ?3 ", 
        nativeQuery = true)
    public List<FTransactionDetails> reportDayWithDate(String status, int field_id, String booking_date, Pageable pageable);

    public Page<FTransactionDetails> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM ftransaction_details fd JOIN field_transactions ft on ft.id = fd.field_transactions_id WHERE fd.is_checkin = 0 AND fd.booking_date = ?1 AND ft.status = ?2", nativeQuery = true)
    public Iterable<FTransactionDetails> todayCheckin(String booking_date, String Status);

    @Query(value = "SELECT * FROM ftransaction_details WHERE is_checkin = 0", nativeQuery = true)
    public Iterable<FTransactionDetails> renderCheck();
    
 
}