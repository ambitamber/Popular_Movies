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
    public final static String API_KEY = "";
    public final static String MOVIE_LANGUAGE = "language";
    public final static String LANGUAGE = "en-us";

    //Use for MainAcitivty - SearchQuery
    public static final String SORT_TOP_RATED = "top_rated";
    public static final String SORT_FAVORITE = "favorite";
    public static final String SORT_POPULAR = "popular";
}
