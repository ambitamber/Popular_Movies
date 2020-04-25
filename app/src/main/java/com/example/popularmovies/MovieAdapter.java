package com.example.popularmovies;

import android.annotation.SuppressLint;
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

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private Movie[] mMovieData;
    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w500";
    FormatDate formatDate;

    private final MovieAdapterOnClickHandler mClickHandler;

    interface MovieAdapterOnClickHandler {
        void onClick(String title,String plot,String releasedate,String rating,String imageposter);
    }

    public MovieAdapter( MovieAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public class MovieAdapterVieweHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mMovieIV;
        final TextView mTitleTV,mRatingTV,mReleasedateTV;

        public MovieAdapterVieweHolder(@NonNull View itemView) {
            super(itemView);
            mMovieIV = itemView.findViewById(R.id.iv_movie_picture);
            mTitleTV = itemView.findViewById(R.id.TV_title);
            mRatingTV = itemView.findViewById(R.id.TV_rating);
            mReleasedateTV = itemView.findViewById(R.id.TV_releasedate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition  = getAdapterPosition();
            String title = mMovieData[adapterPosition].getMovieTitle();
            String plot = mMovieData[adapterPosition].getMoviePlot();
            String releasedate = mMovieData[adapterPosition].getMovieRelease();
            String imageposter = mMovieData[adapterPosition].getMovieImagePoster();
            String rating = mMovieData[adapterPosition].getMovieRating();
            mClickHandler.onClick(title,plot,releasedate,rating,imageposter);
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
        final String title = mMovieData[position].getMovieTitle();
        holder.mTitleTV.setText(title);
        //To view Movie Rating into TextView
        String rating = mMovieData[position].getMovieRating();
        holder.mRatingTV.setText("Rating: " + rating);
        formatDate = new FormatDate();
        String releasedate = formatDate.dateTime(mMovieData[position].getMovieRelease());
        holder.mReleasedateTV.setText(releasedate);
        //To view Movie Poster into Imageview
        final String imagePath = BASE_URL_IMAGE + mMovieData[position].getMovieImagePoster();
        Picasso.get()
                .load(imagePath)
                .into(holder.mMovieIV, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mMovieIV.setVisibility(View.VISIBLE);
                        Log.i(TAG,"Image Path: "+ imagePath);
                    }

                    @Override
                    public void onError(Exception e) {
                        //if there is no image on the server for current movie, it will set the imageview to error_image_loading.png
                        holder.mMovieIV.setImageResource(R.drawable.error_image_loading);
                        Log.e(TAG, title+": There is Image Poster for this Movie.",e);
                    }
                });
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
