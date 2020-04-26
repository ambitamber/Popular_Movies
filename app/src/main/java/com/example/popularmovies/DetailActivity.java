package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    String title = null;
    String plot = null;
    String releasedate = null;
    String rating = null;
    String poster = null;

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
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            title = bundle.getString(Constants.intent_TITLE);
            plot = bundle.getString(Constants.intent_PLOT);
            releasedate = bundle.getString(Constants.intent_RELEASEDATE);
            rating = bundle.getString(Constants.intent_Rating);
            poster = bundle.getString(Constants.intent_IMAGE);
        }

        title_TV.setText(title);
        plot_TV.setText(plot);
        releasedate_TV.setText("Release Date: "+ FormatDate.dateTime(releasedate));
        rating_TV.setText("Rating: "+rating);
        Picasso.get()
                .load(BASE_URL_IMAGE + poster).
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
        setTitle(title);
    }

}
