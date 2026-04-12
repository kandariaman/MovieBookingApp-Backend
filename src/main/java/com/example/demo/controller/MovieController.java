package com.example.demo.controller;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.basicDetails.MovieResponse;
import com.example.demo.service.GetMovies;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    public final GetMovies movieService;

    @GetMapping("/now-playing")
    public Mono<MovieResponse> getFullResponse() {
        return movieService.getNowPlaying();
    }

    @GetMapping("/search")
    public Mono<MovieResponse> searchMovies(@RequestParam String movieName) {

        System.out.println(">>> Backend received search request for: " + movieName);

        return movieService.searchMovies(movieName);
    }

    @GetMapping("/details/{id}")
    public Mono<MovieDetailDTO> getMovieDetails(@PathVariable String id) {

        System.out.println(">>> Backend received details request for: " + id);

        return movieService.getMovieDetails(id);
    }
}