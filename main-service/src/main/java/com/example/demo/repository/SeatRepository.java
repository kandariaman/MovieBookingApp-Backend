package com.example.demo.repository;

import com.example.demo.Seat;
import com.example.demo.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Seat s Set s.status = :available, s.user_id=null, s.locked_at=null " +
    " WHERE s.id = :id AND s.status = :lockedStatus AND s.locked_at < :cutoff")
    void unlockIfExpired(
            @Param("id") Long seatId,
            @Param("cutoff") Instant cutoff,
            @Param("available") Status available,
            @Param("lockedStatus") Status locked);

    @Query("SELECT s FROM Seat s WHERE s.show_id = :id")
    List<Seat> findByShowId(@Param("id") Long showId);}
