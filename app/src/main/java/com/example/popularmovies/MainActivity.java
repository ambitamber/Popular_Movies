package com.example.popularmovies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.adapter.MovieAdapter;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utilities.Constants;
import com.example.popularmovies.utilities.MovieJsonUtils;
import com.example.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private Movie[] mMovie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mMovieAdapter = new MovieAdapter(this);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        //Charged to GridLayoutManager
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData("top_rated");
        setTitle("Top Rated Movies");
    }
    private void loadMovieData(String word) {
        showMovieDataView();
        new FetchMovieTask().execute(word);
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(String title,String plot,String releasedate,String rating,String imageposter) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.intent_TITLE, title);
        intent.putExtra(Constants.intent_Rating,rating);
        intent.putExtra(Constants.intent_IMAGE,imageposter);
        intent.putExtra(Constants.intent_PLOT,plot);
        intent.putExtra(Constants.intent_RELEASEDATE,releasedate);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        Toast.makeText(this,title,Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("StaticFieldLeak")
    public class FetchMovieTask extends AsyncTask<String,Void,Movie[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... strings) {

            if (strings.length == 0){
                return null;
            }
            String query = strings[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(query);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                Movie[] simpleJsonWeatherData = MovieJsonUtils.getSimpleMovieStringFromJson(jsonMovieResponse);
                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieDataView();
                mMovieAdapter.setmMovieData(movies);
            } else {
                showErrorMessage();
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.most_populor:
                loadMovieData("popular");
                setTitle("Popular Movies");
                return true;
            case R.id.highest_rated:
                loadMovieData("top_rated");
                setTitle("Top Rated Movies");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
