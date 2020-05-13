package com.example.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String releaseDate;
    private String rating;
    private String plot;
    private String image;


    public Movie(String id,String title, String releaseDate, String rating, String plot, String image) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.image = image;
        this.plot = plot;
        this.rating = rating;
    }

    //Movie ID
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.title = id;
    }

    //For Movie Title
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    //For Movie Release
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }
    public String getReleaseDate(){
        return releaseDate;
    }

    // Movie Rating
    public void setRating(String rating){
        this.rating = rating;
    }
    public String getRating(){
        return rating;
    }

    //For Movie Plot
    public void setPlot(String plot){
        this.plot = plot;
    }
    public String getPlot(){
        return plot;
    }

    //For Movie Image Poster
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return image;
    }
}
