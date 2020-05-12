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

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private ArrayList<Movie> mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mMovieAdapter = new MovieAdapter(this);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        //GridLayoutManager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData("top_rated");
        setTitle("Top Rated Movies");
    }
    private void loadMovieData(String word) {
        showMovieDataView();
        URL query = NetworkUtils.buildUrl(word);
        new FetchMovieTask().execute(query);
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
    public void onClick(Movie i) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movieItem",i);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }


    @SuppressLint("StaticFieldLeak")
    public class FetchMovieTask extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... strings) {

            if (strings.length == 0){
                return null;
            }
            URL query = strings[0];
            String searchResults = null;

            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(query);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null  && !movies.equals("")) {
                showMovieDataView();
                mMovie = (ArrayList<Movie>) MovieJsonUtils.getSimpleMovieStringFromJson(movies);
                mMovieAdapter.setmMovieData(mMovie);
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
