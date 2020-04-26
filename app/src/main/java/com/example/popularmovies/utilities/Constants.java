package com.example.popularmovies.utilities;

public class Constants {

    private Constants(){
    }
    //This is for MoiveJsonUtils Class.
    public final static String movie_Title = "title";
    public final static String movie_Rating = "vote_average";
    public final static String movie_PosterImage  = "poster_path";
    public final static String movie_Plot = "overview";
    public final static String movie_Release = "release_date";

    //For NetworkUtils
    public final static String STATIC_MOIVES_URL = "https://api.themoviedb.org/3/movie/";
    public final static String MOVIE_API_KEY = "api_key";
    public final static String API_KEY = "b640aea96524ead852f99db7104962de";
    public final static String MOVIE_LANGUAGE = "language";
    public final static String LANGUAGE = "en-us";

    //Use for Intent passing data
    public final static String intent_TITLE = "title";
    public final static String intent_Rating = "rating";
    public final static String intent_IMAGE = "image";
    public final static String intent_PLOT = "plot";
    public final static String intent_RELEASEDATE = "releasedate";

}
