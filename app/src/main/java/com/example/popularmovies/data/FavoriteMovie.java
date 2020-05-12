package com.example.popularmovies.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="FavMovies")
public class FavoriteMovie {
    @PrimaryKey
    private int id;
    private String title;
    private String releaseDate;
    private String rating;
    private String plot;
    private String image;


    public FavoriteMovie(){}


    public FavoriteMovie(int id, String title, String releaseDate, String vote, String synopsis, String image) {

        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = vote;
        this.plot = synopsis;
        this.image = image;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;

    }
    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
