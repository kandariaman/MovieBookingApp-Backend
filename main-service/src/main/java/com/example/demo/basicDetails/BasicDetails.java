package com.example.demo.basicDetails;

import com.example.demo.model.GenresItem;

import java.util.List;

public class BasicDetails {
	private String originalLanguage;
	private String imdbId;
	private boolean video;
	private String title;
	private String backdropPath;
	private int revenue;
	private List<GenresItem> genres;
	private Object popularity;
	private List<ProductionCountriesItem> productionCountries;
	private int id;
	private int voteCount;
	private int budget;
	private String overview;
	private String originalTitle;
	private int runtime;
	private String posterPath;
	private List<String> originCountry;
	private List<SpokenLanguagesItem> spokenLanguages;
	private List<ProductionCompaniesItem> productionCompanies;
	private String releaseDate;
	private Object voteAverage;
	private Object belongsToCollection;
	private String tagline;
	private boolean adult;
	private String homepage;
	private String status;

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public String getImdbId(){
		return imdbId;
	}

	public boolean isVideo(){
		return video;
	}

	public String getTitle(){
		return title;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public int getRevenue(){
		return revenue;
	}

	public List<GenresItem> getGenres(){
		return genres;
	}

	public Object getPopularity(){
		return popularity;
	}

	public List<ProductionCountriesItem> getProductionCountries(){
		return productionCountries;
	}

	public int getId(){
		return id;
	}

	public int getVoteCount(){
		return voteCount;
	}

	public int getBudget(){
		return budget;
	}

	public String getOverview(){
		return overview;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public int getRuntime(){
		return runtime;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public List<String> getOriginCountry(){
		return originCountry;
	}

	public List<SpokenLanguagesItem> getSpokenLanguages(){
		return spokenLanguages;
	}

	public List<ProductionCompaniesItem> getProductionCompanies(){
		return productionCompanies;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public Object getVoteAverage(){
		return voteAverage;
	}

	public Object getBelongsToCollection(){
		return belongsToCollection;
	}

	public String getTagline(){
		return tagline;
	}

	public boolean isAdult(){
		return adult;
	}

	public String getHomepage(){
		return homepage;
	}

	public String getStatus(){
		return status;
	}
}