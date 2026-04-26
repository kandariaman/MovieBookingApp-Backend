package com.example.demo.service;

import com.example.demo.Seat;
import com.example.demo.Status;
import com.example.demo.exception.SeatAlreadyTakenException;
import com.example.demo.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SeatServiceTest {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatRepository seatRepository;

    @Test
    void testSuccessfulReservation() {
        // Create and save available seat
        Seat seat = new Seat();
        seat.setStatus(Status.AVAILABLE);
        seat = seatRepository.save(seat);

        // Run the logic
        seatService.reserveSeat(seat.getId(), 101L);

        // Verify the logic
        Seat updatedSeat = seatRepository.findById(seat.getId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        assertEquals(Status.LOCKED, updatedSeat.getStatus());
        assertEquals(101L, updatedSeat.getUser_id());
        assertNotNull(updatedSeat.getLocked_at());
    }

    @Test
    void testReservationFailsIfAlreadyLocked() {
        // 1. Create and save a seat that is already LOCKED
        Seat seat = new Seat();
        seat.setStatus(Status.LOCKED);
        seat = seatRepository.save(seat);

        // 2. Assert that calling reserveSeat throws the custom exception
        Long seatId = seat.getId();
        assertThrows(SeatAlreadyTakenException.class, () -> {
            seatService.reserveSeat(seatId, 102L);
        });
    }
}