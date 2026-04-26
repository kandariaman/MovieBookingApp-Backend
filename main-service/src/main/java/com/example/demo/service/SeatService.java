package com.example.demo.service;

import reactor.core.scheduler.Schedulers;
import com.example.demo.Seat;
import com.example.demo.Status;
import com.example.demo.exception.SeatAlreadyTakenException;
import com.example.demo.model.ReservationRequest;
import com.example.demo.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public void reserveSeat(Long seatId, Long userId) {

        System.out.println("Seat service is called :::::::");
        // Unlock if more than 10 seconds have passed
        seatRepository.unlockIfExpired(seatId,
                Instant.now().minus(10, ChronoUnit.MINUTES),
                Status.AVAILABLE,
                Status.LOCKED);

        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));

        //2 Check if it is still available
        if(seat.getStatus() != Status.AVAILABLE) {
            throw new SeatAlreadyTakenException();
        }

        // 3 Booking the seat
        seat.setStatus(Status.LOCKED);
        seat.setUser_id(userId);
        seat.setLocked_at(Instant.now());

        try {
            seatRepository.save(seat);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new SeatAlreadyTakenException("Oops! Someone else just grabbed this seat.");
        }


    }

    public Flux<Seat> showSeats(Long showId) {
        return Flux.defer(() -> {
            // 1. Check if seats already exist for this show
            List<Seat> existingSeats = seatRepository.findByShowId(showId);

            if (existingSeats.isEmpty()) {
                // 2. If no seats exist, generate a default set (e.g., 50 seats)
                List<Seat> newSeats = new ArrayList<>();
                for (int i = 1; i <= 50; i++) {
                    Seat seat = new Seat();
                    seat.setShow_id(showId);
                    seat.setStatus(Status.AVAILABLE); // Uses your Enum
                    seat.setVersion(0);
                    // If you add a seatNumber field later: seat.setSeatNumber("S" + i);
                    newSeats.add(seat);
                }
                // 3. Save them all to the DB and return them
                return Flux.fromIterable(seatRepository.saveAll(newSeats));
            }

            // 4. If they already exist, just return them
            return Flux.fromIterable(existingSeats);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
