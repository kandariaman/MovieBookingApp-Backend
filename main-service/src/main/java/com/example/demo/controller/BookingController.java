package com.example.demo.controller;

import com.example.demo.dto.ShowtimeDTO;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5174") // Allows React to connect
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/schedule")
    public ResponseEntity<List<ShowtimeDTO>> getSchedule(
                @RequestParam Long movieId,
                @RequestParam String movieTitle,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date) {
            return ResponseEntity.ok(bookingService.getWeeklySchedule(movieId, movieTitle, date));
        }
    }
