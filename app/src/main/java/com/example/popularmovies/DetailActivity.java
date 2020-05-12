package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utilities.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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

    //This is to get information from MainActivity using Intents
    Movie movie;

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
        if (intent != null){
            movie = intent.getParcelableExtra("movieItem");
        }
        fillData();
    }

    @SuppressLint("SetTextI18n")
    private void fillData() {
        title_TV.setText(movie.getMovieTitle());
        plot_TV.setText(movie.getMoviePlot());
        releasedate_TV.setText("Release Date: "+ FormatDate.dateTime(movie.getMovieRelease()));
        rating_TV.setText("Rating: "+movie.getMovieRating());
        Picasso.get()
                .load(BASE_URL_IMAGE + movie.getMovieImagePoster()).
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
        setTitle(movie.getMovieTitle());
    }

}
