package com.example.popularmovies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MovieJsonUtils {

    private static final String LOG_TAG = MovieJsonUtils.class.getSimpleName();

    public static List<Movie> getSimpleMovieStringFromJson(String movieJsonStr) {

        if (TextUtils.isEmpty(movieJsonStr)){
            return null;
        }

        List<Movie> movieData = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(movieJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int eachMovie = 0; eachMovie < jsonArray.length(); eachMovie++){
                JSONObject currentMovie = jsonArray.getJSONObject(eachMovie);
                String title = currentMovie.optString(Constants.movie_Title);
                String rating = currentMovie.optString(Constants.movie_Rating);
                String image = currentMovie.getString(Constants.movie_PosterImage);
                String plot = currentMovie.optString(Constants.movie_Plot);
                String release = currentMovie.optString(Constants.movie_Release);
                Movie movie = new Movie(title,release,image,plot,rating);
                movieData.add(movie);
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the JSON data", e);
        }
        return movieData;
    }
}
