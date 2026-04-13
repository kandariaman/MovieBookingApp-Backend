package com.example.demo.basicDetails;

import com.example.demo.model.Movie;

import java.util.List;

public class MovieResponse {
	private NowPlayingDates dates;
	private int page;
	private int totalPages;
	private List<Movie> results;
	private int totalResults;

	public NowPlayingDates getDates(){
		return dates;
	}

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<Movie> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}