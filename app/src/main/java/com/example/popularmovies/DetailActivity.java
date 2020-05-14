package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.adapter.TrailerAdapter;
import com.example.popularmovies.data.FavoriteMovie;
import com.example.popularmovies.data.MovieDb;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.utilities.AppExecutors;
import com.example.popularmovies.utilities.FormatDate;
import com.example.popularmovies.utilities.MovieJsonUtils;
import com.example.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener{

    //Link for server to get Image.
    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w500";
    //Giving name for ImageView and TextView
    @BindView(R.id.moviePoster_IV)
    ImageView poster_IV;
    @BindView(R.id.movieTitle_TV)
    TextView title_TV;
    @BindView(R.id.moviePlot_TV)
    TextView plot_TV;
    @BindView(R.id.movieReleasedate_TV)
    TextView releasedate_TV;
    @BindView(R.id.movieRating_TV)
    TextView rating_TV;

    private Movie movie;
    private ArrayList<Review> reviews;
    private ArrayList<Trailer> trailers;

    //For Trailers
    private RecyclerView recyclerView;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //For Favorite movies
    private MovieDb movieDb;
    private ImageView favButton;
    private Boolean isFavorite = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        poster_IV = findViewById(R.id.moviePoster_IV);
        title_TV = findViewById(R.id.movieTitle_TV);
        plot_TV = findViewById(R.id.moviePlot_TV);
        releasedate_TV = findViewById(R.id.movieReleasedate_TV);
        rating_TV = findViewById(R.id.movieRating_TV);

        Intent intent = getIntent();
        if (intent == null){
            giveError("No data for Intent");
        }
        assert intent != null;
        movie = (Movie) intent.getSerializableExtra("movieItem");
        if (movie == null){
            giveError("No Movie Data");
            return;
        }

        //RecyclerView for Movie Trailer
        recyclerView = findViewById(R.id.trailer_RecyclerView);
        mTrailerAdapter = new TrailerAdapter(this,trailers,this);
        recyclerView.setAdapter(mTrailerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Favorite Movie
        favButton = findViewById(R.id.favButton_IV);
        movieDb = MovieDb.getInstance(getApplicationContext());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final FavoriteMovie favoriteMovie = movieDb.movieDao().loadMovieById(Integer.parseInt(movie.getId()));
                setFavorite((favoriteMovie != null)? true : false);
            }
        });

        getMoreDetails(movie.getId());
    }

    private void setFavorite(boolean b) {
        if (b){
            isFavorite = true;
            favButton.setImageResource(R.drawable.favorite_solid);
        }else {
            isFavorite = false;
            favButton.setImageResource(R.drawable.favorite_border_black);
        }
    }

    private static class SearchURLs {
        URL reviewSearchUrl;
        URL trailerSearchUrl;
        SearchURLs(URL reviewSearchUrl, URL trailerSearchUrl){
            this.reviewSearchUrl = reviewSearchUrl;
            this.trailerSearchUrl = trailerSearchUrl;
        }
    }
    private static class ResultsStrings {
        String reviewString;
        String trailerString;
        ResultsStrings(String reviewString, String trailerString){
            this.reviewString = reviewString;
            this.trailerString = trailerString;
        }
    }

    private void getMoreDetails(String id) {
        String reviewQuery = id + File.separator + "reviews";
        String trailerQuery = id + File.separator +  "videos";

        SearchURLs searchURLs = new SearchURLs(
                NetworkUtils.buildUrl(reviewQuery),
                NetworkUtils.buildUrl(trailerQuery)
        );
        new ReviewsQueryTask().execute(searchURLs);
    }
    @SuppressLint("StaticFieldLeak")
    private class ReviewsQueryTask extends AsyncTask<SearchURLs,Void,ResultsStrings> {

        @Override
        protected ResultsStrings doInBackground(SearchURLs... searchURLs) {

            URL reviewsearchUrl = searchURLs[0].reviewSearchUrl;
            URL trailersearchUrl = searchURLs[0].trailerSearchUrl;
            String reviewResults = null;
            try {
                reviewResults = NetworkUtils.getResponseFromHttpUrl(reviewsearchUrl);
                reviews = MovieJsonUtils.getSimpleReviewStringFromJson(reviewResults);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String trailerResults = null;
            try {
                trailerResults = NetworkUtils.getResponseFromHttpUrl(trailersearchUrl);
                trailers = MovieJsonUtils.getSimpleTrailerStringFromJson(trailerResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResultsStrings(reviewResults,trailerResults);
        }

        @Override
        protected void onPostExecute(ResultsStrings resultsStrings) {
            String results = resultsStrings.reviewString;
            if (results != null && !results.equals("")){
                reviews = MovieJsonUtils.getSimpleReviewStringFromJson(results);
                fillData();
            }
        }
    }

    @Override
    public void onClick(Trailer id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id.getKey()));
        webIntent.putExtra("finish_on_ended", true);
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
        Log.d("DetailActivty: ",id.getKey());
    }

    private void giveError(String msg) {
        finish();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void fillData() {
        setTitle(movie.getTitle());
        title_TV.setText(movie.getTitle());
        plot_TV.setText(movie.getPlot());
        releasedate_TV.setText("Release Date: "+ FormatDate.dateTime(movie.getReleaseDate()));
        rating_TV.setText("Rating: "+movie.getRating());
        Picasso.get()
                .load(BASE_URL_IMAGE + movie.getImage()).
                into(poster_IV, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        poster_IV.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        poster_IV.setImageResource(R.drawable.error_image_loading);
                    }
                });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FavoriteMovie favoriteMovie = new FavoriteMovie(
                        Integer.parseInt(movie.getId()),
                        movie.getTitle(),
                        movie.getReleaseDate(),
                        movie.getRating(),
                        movie.getPlot(),
                        movie.getImage()
                );
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (isFavorite){
                            movieDb.movieDao().deleteMovie(favoriteMovie);
                        }else {
                            movieDb.movieDao().insertMovie(favoriteMovie);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setFavorite(!isFavorite);
                            }
                        });
                    }
                });
            }
        });
        mTrailerAdapter.setTrailerData(trailers);
    }
}
