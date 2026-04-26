package com.example.demo.controller;

import com.example.demo.Seat;
import com.example.demo.basicDetails.MovieResponse;
import com.example.demo.exception.SeatAlreadyTakenException;
import com.example.demo.model.ReservationRequest;
import com.example.demo.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatingController {

    private final SeatService seatService;

    @GetMapping("/show/{showId}")
    public Flux<Seat> getSeats(@PathVariable("showId") Long id) {
        return seatService.showSeats(id);
    }

    @PostMapping("/{seatId}/reserve")
    public ResponseEntity<?> reserve(@PathVariable Long seatId, @RequestBody ReservationRequest req) {

        try{
            seatService.reserveSeat(seatId, req.getUserId());
            return ResponseEntity.ok().build();
        } catch (SeatAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
}
