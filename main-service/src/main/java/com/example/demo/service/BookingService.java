package com.example.demo.service;

import com.example.demo.Screening;
import com.example.demo.Theater;
import com.example.demo.dto.ShowtimeDTO;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ScreeningRepository;
import com.example.demo.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {


    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public List<ShowtimeDTO> getWeeklySchedule(Long movieId, String movieTitle, LocalDate date) {
        List<Screening> screenings = screeningRepository.findByMovieAndDate(movieId, date);

        if (screenings.isEmpty()) {
            screenings = generateScreeningsForMovie(movieId, movieTitle, date);
        }

        // 3. Convert to DTO and return
        return getBookMyShowStyleschedule(movieId, date, screenings);

//        return screenings.stream()
//                .collect(Collectors.groupingBy(Screening::getTheater))
//                .entrySet()
//                .stream()
//                .map(entry -> new ShowtimeDTO(entry.getKey().getName(),
//                        entry.getKey().getLocation(),
//                        entry.getValue()
//                                .stream()
//                                .map(d -> d.getShowTime())
//                                .sorted()
//                                .collect(Collectors.toList())
//                )).collect(Collectors.toList());
    }

    public List<Screening> generateScreeningsForMovie(Long movieId, String movieTitle,  LocalDate date) {
        Movie movie = movieRepository.findById(movieId).orElseGet(
                () -> {
                    Movie newM = new Movie();
                    newM.setId(movieId);
                    newM.setTitle(movieTitle);
                    return movieRepository.save(newM);
                }
        );

        List<Theater> allTheaters = theaterRepository.findAll();
        return screeningRepository.saveAll(setScreening(allTheaters, movie));
    }

    public List<Screening> setScreening(List<Theater> theaters, Movie movie){
        LocalDate today = LocalDate.now();
        List<Screening> generatedScreenings = new ArrayList<>();
        List<Screening> finalScreenings = new ArrayList<>();


        for(int i = 0; i <= 7; i++) {
                LocalDate date = today.plusDays(i);

                for(Theater t : theaters){
                    generatedScreenings.add(saveScreening(t, date, LocalTime.of(10, 0), new BigDecimal("250.00"), movie));
                    generatedScreenings.add(saveScreening(t, date, LocalTime.of(14, 30), new BigDecimal("300.00"), movie));
                    generatedScreenings.add(saveScreening(t, date, LocalTime.of(19, 0), new BigDecimal("350.00"), movie));
                }
    }

        for (Screening s : generatedScreenings) {
            // Check if this specific combo exists before adding it to the list
            if (!screeningRepository.existsByTheaterAndMovieAndShowDateAndShowTime(
                    s.getTheater(), s.getMovie(), s.getShowDate(), s.getShowTime())) {
                finalScreenings.add(s);
            }
        }

        return finalScreenings;
    }

    private Screening saveScreening(Theater t, LocalDate d, LocalTime time, BigDecimal p, Movie movie) {
        Screening s = new Screening();
        s.setTheater(t);
        s.setShowDate(d);
        s.setShowTime(time);
        s.setPrice(p);
        s.setMovie(movie);
        return s;
    }

    public List<ShowtimeDTO> getBookMyShowStyleschedule(Long movieId, LocalDate date, List<Screening> screenings) {
//        List<Screening> screenings = screeningRepository.findByMovieAndDate(movieId, date);

        // Grouping the flat list into a Map: Theater -> List of Screenings

        List<Screening> screeningsForDate = screenings.stream()
                .filter(s -> s.getShowDate().equals(date))
                .toList();

        return screeningsForDate.stream()
                .collect(Collectors.groupingBy(s -> s.getTheater()))
                .entrySet().stream()
                .map(entry -> {
                    Theater theater = entry.getKey();
                    List<Screening> theaterScreenings = entry.getValue();

                    ShowtimeDTO dto = new ShowtimeDTO();
                    dto.setTheaterName(theater.getName());
                    dto.setLocation(theater.getLocation());

                    List<Long> screenIds = theaterScreenings.stream()
                            .filter(s -> {
                                return s.getShowDate().equals(date);

                            })
                                    .map(Screening::getId)
                                            .toList();

                    dto.setScreeningIds(screenIds);

                    // Extract and sort timings for this specific theater
                    dto.setTimings(theaterScreenings.stream()
                            .map(Screening::getShowTime)
                            .distinct()
                            .sorted()
                            .toList());

//                    dto.setScreeningIds();

                    return dto;
                })
                .toList();
    }
}