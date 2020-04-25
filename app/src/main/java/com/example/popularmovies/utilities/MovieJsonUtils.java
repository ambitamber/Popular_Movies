package com.example.popularmovies.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MovieJsonUtils {

    private static final String LOG_TAG = MovieJsonUtils.class.getSimpleName();

    public static Movie[] getSimpleMovieStringFromJson(Context context, String movieJsonStr) throws JSONException {

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
                movie.setTitle(jsonArray.getJSONObject(eachMovie).optString("title"));
                movie.setMovieRating(jsonArray.getJSONObject(eachMovie).optString("vote_average"));
                movie.setImagePoster(jsonArray.getJSONObject(eachMovie).getString("poster_path"));
                movie.setPlot(jsonArray.getJSONObject(eachMovie).optString("overview"));
                movie.setReleaseDate(jsonArray.getJSONObject(eachMovie).optString("release_date"));
                movieData[eachMovie] = movie;
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the JSON data", e);
        }
        return movieData;
    }
}
