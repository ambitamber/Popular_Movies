package com.example.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String releaseDate;
    private String rating;
    private String plot;
    private String image;


    public Movie(String id, String title, String releaseDate, String rating, String plot, String image) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.plot = plot;
        this.image = image;
    }

    //Movie ID
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.title = id;
    }

    //For Movie Title
    public void setTitle(String mMovieTitle){
        title = mMovieTitle;
    }
    public String getTitle(){
        return title;
    }

    //For Movie Release
    public void setReleaseDate(String mMovieReleaseDate){
        releaseDate = mMovieReleaseDate;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

    //For Movie Image Poster
    public void setImagePoster(String mMovieRelease){
        image = mMovieRelease;
    }
    public String getImage(){
        return image;
    }

    //For Movie Plot
    public void setPlot(String mMoviePlot){
        plot = mMoviePlot;
    }
    public String getPlot(){
        return plot;
    }

    // Movie Rating
    public void setRating(String mMovieRating){
        rating = mMovieRating;
    }
    public String getRating(){
        return rating;
    }

}
