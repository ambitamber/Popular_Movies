package com.example.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
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
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(String mMovieTitle, String mMovieRelease, String mMovieImagePoster,String mMoviePlot,String mMovieRating) {
        this.movieTitle = mMovieTitle;
        this.movieRelease = mMovieRelease;
        this.movieImagePoster = mMovieImagePoster;
        this.moviePlot = mMoviePlot;
        this.movieRating = mMovieRating;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(movieRelease);
        dest.writeString(movieImagePoster);
        dest.writeString(moviePlot);
        dest.writeString(movieRating);
    }
}
