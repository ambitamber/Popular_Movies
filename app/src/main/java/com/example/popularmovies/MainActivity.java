package com.example.popularmovies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.adapter.MovieAdapter;
import com.example.popularmovies.data.FavoriteMovie;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utilities.Constants;
import com.example.popularmovies.utilities.MovieJsonUtils;
import com.example.popularmovies.utilities.NetworkUtils;
import com.example.popularmovies.viewmodel.MainViewModel;

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
    private List<FavoriteMovie> favMovs;
    private static final String SORT_POPULAR = Constants.SORT_POPULAR;
    private static String currentSort = SORT_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mMovieAdapter = new MovieAdapter(this,this,mMovie);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        //GridLayoutManager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        favMovs = new ArrayList<>();


        getViewModel();
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

    private void getViewModel(){
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                if (favoriteMovies.size() > 0){
                    favMovs.clear();
                    favMovs = favoriteMovies;
                }
                for (int i = 0; i < favMovs.size(); i++){
                    Log.d("MainActivity: ",favMovs.get(i).getTitle());
                }
                searchQuery();
            }
        });
    }

    private void searchQuery() {
        if (currentSort.equals(Constants.SORT_FAVORITE)){
            clearList();
            for (int i = 0; i < favMovs.size(); i++){
                Movie movie = new Movie(
                        String.valueOf(favMovs.get(i).getId()),
                        favMovs.get(i).getTitle(),
                        favMovs.get(i).getReleaseDate(),
                        favMovs.get(i).getRating(),
                        favMovs.get(i).getPlot(),
                        favMovs.get(i).getImage()
                );
                mMovie.add(movie);
            }
            mMovieAdapter.setmMovieData(mMovie);
        }else {
            String query = currentSort;
            URL searchUrl = NetworkUtils.buildUrl(query);
            new FetchMovieTask().execute(searchUrl);
        }
    }

    private void clearList() {
        if (mMovie != null) {
            mMovie.clear();
        } else {
            mMovie = new ArrayList<>();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.most_populor && !currentSort.equals(SORT_POPULAR)){
            clearList();
            currentSort = SORT_POPULAR;
            setTitle("Popular Movies");
            searchQuery();
            return true;
        } else if (id == R.id.highest_rated && !currentSort.equals(Constants.SORT_TOP_RATED)){
            clearList();
            currentSort = Constants.SORT_TOP_RATED;
            setTitle("Top Rated Movies");
            searchQuery();
            return true;
        } else if (id == R.id.favorite && !currentSort.equals(Constants.SORT_FAVORITE)){
            clearList();
            currentSort = Constants.SORT_FAVORITE;
            setTitle("Favorite Movies");
            searchQuery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
