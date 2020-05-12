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



    public String getVote() {
        return rating;
    }
    public void setVote(String vote) {
        this.rating = vote;
    }


    public String getSynopsis() {
        return plot;

    }
    public void setSynopsis(String synopsis) {
        this.plot = synopsis;
    }


    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
