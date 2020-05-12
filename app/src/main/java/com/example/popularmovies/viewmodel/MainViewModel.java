package com.example.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.data.FavoriteMovie;
import com.example.popularmovies.data.MovieDb;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMovie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        MovieDb db = MovieDb.getInstance(this.getApplication());
        movies = db.movieDao().loadAllMovies();
    }
    public LiveData<List<FavoriteMovie>> getMovies(){
        return movies;
    }
}
