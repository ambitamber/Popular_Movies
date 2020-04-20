package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterVieweHolder>{

    private String[] mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    interface MovieAdapterOnClickHandler {
        void onClick(String weatherforDay);
    }

    public MovieAdapter( MovieAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public class MovieAdapterVieweHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mMovieTV;

        public MovieAdapterVieweHolder(@NonNull View itemView) {
            super(itemView);
            mMovieTV = itemView.findViewById(R.id.tv_movie_data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition  = getAdapterPosition();
            String eachMovie = mMovieData[adapterPosition];
            mClickHandler.onClick(eachMovie);
        }
    }

    @NonNull
    @Override
    public MovieAdapterVieweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForItem = R.layout.list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForItem,parent,false);
        return new MovieAdapterVieweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterVieweHolder holder, int position) {
        String eachMovie = mMovieData[position];
        holder.mMovieTV.setText(eachMovie);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData){
            return 0;
        }
       return mMovieData.length;
    }
    public void setmMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
