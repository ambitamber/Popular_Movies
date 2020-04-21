package com.example.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String movieTitle;
    private String movieRelease;
    private String movieImagePoster;
    private String moviePlot;
    private String movieRating;

    //For Movie Title
    public void setTitle(String mMovieTitle){
        movieTitle = mMovieTitle;
    }
    public String getMovieTitle(){
        return movieTitle;
    }

    //For Movie Release
    public void setReleaseDate(String mMovieReleaseDate){
        movieRelease = mMovieReleaseDate;
    }
    public String getMovieRelease(){
        return movieRelease;
    }

    //For Movie Image Poster
    public void setImagePoster(String mMovieRelease){
        movieImagePoster = mMovieRelease;
    }
    public String getMovieImagePoster(){
        return movieImagePoster;
    }

    //For Movie Plot
    public void setPlot(String mMoviePlot){
        moviePlot = mMoviePlot;
    }
    public String getMoviePlot(){
        return moviePlot;
    }

    // Movie Rating
    public void setMovieRating(String mMovieRating){
        movieRating = mMovieRating;
    }
    public String getMovieRating(){
        return movieRating;
    }
}
