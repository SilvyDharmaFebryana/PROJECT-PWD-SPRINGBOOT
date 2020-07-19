package com.kickoff.kickoff.dao;

import java.util.List;

import com.kickoff.kickoff.entity.Paket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaketRepo extends JpaRepository<Paket, Integer> {

    @Query(value = "SELECT count(*) from paket p JOIN field_transactions ft ON p.id = ft.paket_id WHERE p.id = ?1 AND ft.status = ?2", nativeQuery = true)
    public Long count(int id, String status);

    @Query(value = "select count(*) as jumlah, p.* from paket p join field_transactions ft on p.id = ft.paket_id where ft.status = ?1 group by nama_paket", nativeQuery = true)
    public Iterable<Paket> getReportPaket(String status);

}