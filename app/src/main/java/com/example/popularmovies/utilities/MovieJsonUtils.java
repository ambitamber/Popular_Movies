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
            Log.e("MovieJsonUtils", "Problem parsing the JSON data", e);
        }
        return movieData;
    }
    private static String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
    private static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
