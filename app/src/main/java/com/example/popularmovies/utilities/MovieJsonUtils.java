package com.example.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MovieJsonUtils {

    private static final String LOG_TAG = MovieJsonUtils.class.getSimpleName();

    public static String[] getSimpleMovieStringFromJson(Context context, String movieJsonStr) throws JSONException {

        JSONObject jsonObject = new JSONObject(movieJsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        Log.println(Log.INFO,LOG_TAG,String.valueOf(jsonArray));

        String[] movieData = new String[jsonArray.length()];

        for (int movie = 0; movie < jsonArray.length(); movie++){
            JSONObject currentMovie = jsonArray.getJSONObject(movie);
            String movieTitle = currentMovie.getString("title");
            String relaseDate = currentMovie.getString("release_date");

            movieData[movie] = movieData + " - " + relaseDate;
        }
        return movieData;
    }
}
