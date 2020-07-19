package com.kickoff.kickoff.dao;

import com.kickoff.kickoff.entity.Notif;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotifRepo extends JpaRepository<Notif, Integer> {
    

    @Query(value = "SELECT * FROM Notif WHERE user_id = ?1", nativeQuery = true)
    public Iterable<Notif> findNotifUser(int userId);
}