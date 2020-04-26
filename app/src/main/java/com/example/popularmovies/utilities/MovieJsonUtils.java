package com.example.popularmovies.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class MovieJsonUtils {

    private static final String LOG_TAG = MovieJsonUtils.class.getSimpleName();

    public static Movie[] getSimpleMovieStringFromJson(String movieJsonStr) throws JSONException {

        if (TextUtils.isEmpty(movieJsonStr)){
            return null;
        }
        JSONObject jsonObject = new JSONObject(movieJsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        Log.println(Log.INFO,LOG_TAG,String.valueOf(jsonArray));

        Movie[] movieData = new Movie[jsonArray.length()];
        try {
            for (int eachMovie = 0; eachMovie < jsonArray.length(); eachMovie++){
                Movie movie = new Movie();
                movie.setTitle(jsonArray.getJSONObject(eachMovie).optString(Constants.movie_Title));
                movie.setMovieRating(jsonArray.getJSONObject(eachMovie).optString(Constants.movie_Rating));
                movie.setImagePoster(jsonArray.getJSONObject(eachMovie).getString(Constants.movie_PosterImage));
                movie.setPlot(jsonArray.getJSONObject(eachMovie).optString(Constants.movie_Plot));
                movie.setReleaseDate(jsonArray.getJSONObject(eachMovie).optString(Constants.movie_Release));
                movieData[eachMovie] = movie;
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the JSON data", e);
        }
        return movieData;
    }
}
