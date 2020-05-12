package com.example.popularmovies.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class}, version = 3, exportSchema = false)

public abstract class MovieDb extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movieslist";
    private static MovieDb sInstance;

    public static MovieDb getInstance(Context context) {

        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDb.class, MovieDb.DATABASE_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}