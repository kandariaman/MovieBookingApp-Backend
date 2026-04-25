package com.example.demo.repository;

import com.example.demo.Seat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Seat s Set s.status = 'AVAILABLE', s.user_id=null, s.locked_at=null " +
    " WHERE s.id = :id AND s.status = 'LOCKED' AND s.locked_at < :cutoff")
    void unlockIfExpired(Long seatId, Instant cutoff);
}
