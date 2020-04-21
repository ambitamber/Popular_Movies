package com.example.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterVieweHolder>{

    private Movie[] mMovieData;
    public static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w342";

    private final MovieAdapterOnClickHandler mClickHandler;

    interface MovieAdapterOnClickHandler {
        void onClick(String title,String rating);
    }

    public MovieAdapter( MovieAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public class MovieAdapterVieweHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mMovieIV;
        final TextView mTitleTV,mRatingTV,mPlotTV,mReleasedateTV;


        public MovieAdapterVieweHolder(@NonNull View itemView) {
            super(itemView);
            mMovieIV = itemView.findViewById(R.id.iv_movie_picture);
            mTitleTV = itemView.findViewById(R.id.TV_title);
            mRatingTV = itemView.findViewById(R.id.TV_rating);
            mPlotTV = itemView.findViewById(R.id.TV_plot);
            mReleasedateTV = itemView.findViewById(R.id.TV_releasedate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition  = getAdapterPosition();
            String title = mMovieData[adapterPosition].getMovieTitle();
            String rating = mMovieData[adapterPosition].getMovieRating();
            mClickHandler.onClick(title,rating);
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
        //To view Movie Title into TextView
        String title = mMovieData[position].getMovieTitle();
        holder.mTitleTV.setText(title);
        //To view Movie Plot into TextView
        String plot = mMovieData[position].getMoviePlot();
        holder.mPlotTV.setText(plot);
        //To view Movie Rating into TextView
        String rating = mMovieData[position].getMovieRating();
        holder.mRatingTV.setText("Rating: " + rating);
        String releasedate = mMovieData[position].getMovieRelease();
        holder.mReleasedateTV.setText(releasedate);
        //To view Movie Poster into Imageview
        String imagePath = BASE_URL_IMAGE + mMovieData[position].getMovieImagePoster();
        Picasso.get()
                .load(imagePath)
                .into(holder.mMovieIV);
        Log.i("Image Path: ", imagePath);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData){
            return 0;
        }
       return mMovieData.length;
    }

    public void setmMovieData(Movie[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
