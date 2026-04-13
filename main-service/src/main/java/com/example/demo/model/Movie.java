package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

	@Id // No @GeneratedValue because you are using IDs from the MovieDB API
	@Column(name = "id") // This matches the Primary Key of the 'movies' table
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String overview;

	private String originalLanguage;
	private String originalTitle;
	private boolean video;
	private String title;

	@ElementCollection
	@CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
	private List<Integer> genreIds;

	@JsonProperty("poster_path")
	@Column(name = "poster_path")
	private String posterPath;

	@JsonProperty("backdrop_path")
	@Column(name = "backdrop_path")
	private String backdropPath;

	private String releaseDate;
	private Double popularity;
	private Double voteAverage;
	private boolean adult;
	@Column(nullable = true)
	private Integer voteCount;

	// NO MANUAL GETTERS NEEDED - @Data handles this!
}