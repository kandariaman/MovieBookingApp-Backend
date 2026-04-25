package com.example.demo.service;

import com.example.demo.Seat;
import com.example.demo.Status;
import com.example.demo.exception.SeatAlreadyTakenException;
import com.example.demo.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public void reserveSeat(Long seatId, Long userId) {

        // Unlock if more than 10 seconds have passed
        seatRepository.unlockIfExpired(seatId, Instant.now().minus(10, ChronoUnit.MINUTES));

        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));

        //2 Check if it is still available
        if(seat.getStatus() != Status.AVAILABLE) {
            throw new SeatAlreadyTakenException();
        }

        // 3 Booking the seat
        seat.setStatus(Status.LOCKED);
        seat.setUser_id(userId);
        seat.setLocked_at(Instant.now());

        seatRepository.save(seat);
    }
}
