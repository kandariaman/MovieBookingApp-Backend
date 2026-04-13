package com.example.demo.service;

import com.example.demo.dto.MovieDetailDTO;
import com.example.demo.basicDetails.MovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GetMovies {

    private final WebClient webClient;
    private final String token;

    public GetMovies(WebClient webClient,@Value("${TMDB_TOKEN}") String token) {
        this.webClient = webClient;
        this.token = token;
    }

//    private static final String BASE_URL = "https://api.themoviedb.org";

    public Mono<MovieResponse> getNowPlaying() {
        return webClient.get()
                .uri("/movie/now_playing")
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(MovieResponse.class);
    }

    public Mono<MovieResponse> searchMovies(String movieName) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", movieName)
                        .build())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(MovieResponse.class);
    }
    public Mono<MovieDetailDTO> getMovieDetails(String movieId) {
        return webClient.get()
                .uri("/movie/" + movieId)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(MovieDetailDTO.class);
    }
}
