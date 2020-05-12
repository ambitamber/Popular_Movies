package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Movie implements Serializable {
    private String movieID;
    private String movieTitle;
    private String movieRelease;
    private String movieImagePoster;
    private String moviePlot;
    private String movieRating;

    public Movie(Parcel in) {
        movieTitle = in.readString();
        movieRelease = in.readString();
        movieImagePoster = in.readString();
        moviePlot = in.readString();
        movieRating = in.readString();
        movieID = in.readString();
    }



    public Movie(String Id,String mMovieTitle, String mMovieRelease, String mMovieImagePoster,String mMoviePlot,String mMovieRating) {
        this.movieID = Id;
        this.movieTitle = mMovieTitle;
        this.movieRelease = mMovieRelease;
        this.movieImagePoster = mMovieImagePoster;
        this.moviePlot = mMoviePlot;
        this.movieRating = mMovieRating;
    }

    //Movie ID
    public String getId() {
        return movieID;
    }
    public void setId(String id) {
        this.movieTitle = id;
    }

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
