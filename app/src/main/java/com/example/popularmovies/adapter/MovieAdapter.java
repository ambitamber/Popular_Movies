package com.example.popularmovies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterVieweHolder>{

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private List<Movie> mMovieData;
    private final Context context;
    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w500";


    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler mClickHandler,Context context,List<Movie> movieItemList) {
        this.mClickHandler = mClickHandler;
        this.context = context;
        this.mMovieData = movieItemList;
    }

    public class MovieAdapterVieweHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mMovieIV;

        public MovieAdapterVieweHolder(@NonNull View itemView) {
            super(itemView);
            mMovieIV = itemView.findViewById(R.id.iv_movie_picture);
            itemView.setOnClickListener(this);
        }

        void bind(int index){
            Movie movie = mMovieData.get(index);
            mMovieIV = itemView.findViewById(R.id.iv_movie_picture);
            String imagePath = BASE_URL_IMAGE+movie.getImage();
            Picasso.get()
                    .load(imagePath)
                    .into(mMovieIV, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            mMovieIV.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onError(Exception e) {
                            //if there is no image on the server for current movie, it will set the imageview to error_image_loading.png
                            mMovieIV.setImageResource(R.drawable.error_image_loading);
                        }
                    });
        }

        @Override
        public void onClick(View v) {
            int adapterPosition  = getAdapterPosition();
            Movie movie = mMovieData.get(adapterPosition);
            mClickHandler.onClick(movie);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MovieAdapterVieweHolder holder, int position) {
        //To view Movie Title into TextView
      holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData){
            return 0;
        }
       return mMovieData.size();
    }

    public void setmMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

}
