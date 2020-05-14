package com.example.popularmovies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;

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
                String id = currentMovie.optString("id");
                String title = currentMovie.optString(Constants.movie_Title);
                String rating = currentMovie.optString(Constants.movie_Rating);
                String image = currentMovie.getString(Constants.movie_PosterImage);
                String plot = currentMovie.optString(Constants.movie_Plot);
                String release = currentMovie.optString(Constants.movie_Release);
                Movie movie = new Movie(id,title,release,rating,plot,image);
                movieData.add(movie);
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the JSON data", e);
        }
        return movieData;
    }

    public static ArrayList<Review> getSimpleReviewStringFromJson(String reviewJsonStr) {

        try {
            Review review;
            JSONObject json_object = new JSONObject(reviewJsonStr);
            JSONArray resultsArray = new JSONArray(json_object.optString("results","[\"\"]"));
            ArrayList<Review> reviewList = new ArrayList<>();

            for (int i = 0; i < resultsArray.length(); i++) {
                String thisitem = resultsArray.optString(i, "");
                JSONObject movieJson = new JSONObject(thisitem);

                review = new Review(
                        movieJson.optString("author","Not Available"),
                        movieJson.optString("content","Not Available"),
                        movieJson.optString("id","Not Available"),
                        movieJson.optString("url","Not Available")
                );
                reviewList.add(review);
            }
            return reviewList;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON data under getSimpleReviewStringFromJson: ", e);
        }
            return null;
    }

    public static ArrayList<Trailer> getSimpleTrailerStringFromJson(String trailerJsonStr){
        try {
            Trailer trailer;
            JSONObject json_object = new JSONObject(trailerJsonStr);
            JSONArray resultsArray = new JSONArray(json_object.optString("results","[\"\"]"));
            ArrayList<Trailer> trailerList = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {

                String thisitem = resultsArray.optString(i, "");
                JSONObject movieJson = new JSONObject(thisitem);

                trailer = new Trailer(
                        movieJson.optString("name","Not Available"),
                        movieJson.optString("site","Not Available"),
                        movieJson.optString("key","Not Available"),
                        movieJson.optString("type","Not Available")
                );
                trailerList.add(trailer);
            }
            return trailerList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem parsing the JSON data under getSimpleTrailerStringFromJson: ", e);
        }
        return null;
    }
}
