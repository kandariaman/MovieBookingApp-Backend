package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    private String theaterName;
    private String location;

    // This is the key for BookMyShow style!
    // It groups all times for this specific theater.
    private List<LocalTime> timings;

    // You'll want the IDs so when someone clicks 10:00 AM,
    // you know which Screening ID they are booking.
    private List<Long> screeningIds;

    private List<Integer> availableSeats = List.of(60, 45, 2);

}