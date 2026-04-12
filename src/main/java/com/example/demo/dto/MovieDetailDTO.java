package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieDetailDTO {
    // These names match the JSON exactly, so Jackson maps them automatically
    private String title;
    private String overview;
    private int runtime;

    @JsonProperty("release_date") // Maps "release_date" from JSON to releaseDate variable
    private String releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("vote_average")
    private double rating;

    private List<Genre> genres; // Maps the nested array

    // Standard Getters and Setters

    @Data
    public static class Genre {
        private int id;
        private String name;

        // Getters and Setters
    }
}
