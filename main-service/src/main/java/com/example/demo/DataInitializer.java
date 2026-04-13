package com.example.demo;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ScreeningRepository;
import com.example.demo.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired // Add this!
    private MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. Define real cinemas
        List<Theater> theaters = Arrays.asList(
                new Theater("PVR: Director's Cut", "Vasant Kunj, Delhi"),
                new Theater("INOX: Insignia", "Nehru Place, Delhi"),
                new Theater("Wave Cinemas", "Raja Garden, Delhi"),
                new Theater("PVR: Logix", "Noida"),
                new Theater("M-Cinemas", "East of Kailash, Delhi"),
                new Theater("PVR: Rivoli", "Connaught Place, Delhi"),
                new Theater("Delite Cinema", "Asaf Ali Rd, Delhi"),
                new Theater("Carnival Cinemas", "Odeon, Delhi"),
                new Theater("PVR: Sangam", "R.K. Puram, Delhi"),
                new Theater("Miraj Cinemas", "Subhash Nagar, Delhi")
        );

        for(Theater th : theaters) {
            if (!theaterRepository.existsByNameAndLocation(th.getName(), th.getLocation())) {
                theaterRepository.save(th);
            }
        }
    }

    private void saveScreening(Theater t, LocalDate d, LocalTime time, BigDecimal p, Movie movie) {
        Screening s = new Screening();
        s.setTheater(t);
        s.setShowDate(d);
        s.setShowTime(time);
        s.setPrice(p);
        s.setMovie(movie);
        screeningRepository.save(s);
    }
}
