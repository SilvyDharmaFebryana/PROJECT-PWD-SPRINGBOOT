package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.Field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FieldRepo extends JpaRepository<Field, Integer> {
    
    @Query(value = "SELECT * FROM Field WHERE category = ?1", nativeQuery = true)
    public Iterable<Field> findField(String category);


    @Query(value = "SELECT * FROM Field WHERE type = ?1", nativeQuery = true)
    public Iterable<Field> findType(String type);

    @Query(value = "select * from field where rating <= ?1 and rating > ?2", nativeQuery = true)
    public Iterable<Field> rating(int satu, int dua);

    @Query(value = "select count(*) from field f join ftransaction_details fd on f.id = fd.field_id join field_transactions ft on ft.id = fd.field_transactions_id where fd.field_id = ?1 and ft.status = ?2", nativeQuery = true)
    public Long reportField(int id, String status);

    @Query(value = "select count(*) as jumlah, f.* from ftransaction_details fd join field f on f.id = fd.field_id group by field_name ORDER BY jumlah DESC", nativeQuery = true)    
    public Iterable<Field> getReportCount();

    @Query(value = "select * from field f join ftransaction_details fd on fd.field_id = f.id where f.id = ?1 and fd.is_Checkin = 1", nativeQuery = true)
    public Iterable<Field> getLength(int id);
}