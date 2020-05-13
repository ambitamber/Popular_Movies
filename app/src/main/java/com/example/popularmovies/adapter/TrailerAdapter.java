package com.example.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private final Context context;
    private List<Trailer> trailers;
    final private ListItemClickListener listItemClickListener;


    public interface ListItemClickListener{
        void onClick(Trailer clickItem);
    }

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.trailers = trailers;
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == trailers){
            return 0;
        }
        return trailers.size();
    }
    public void setTrailerData(List<Trailer> trailerItemList) {
        trailers = trailerItemList;
        notifyDataSetChanged();

    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trailerName, trailerSite, trailerType;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.TV_trailerName);
            trailerSite = itemView.findViewById(R.id.TV_trailerSite);
            trailerType = itemView.findViewById(R.id.TV_trailerType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listItemClickListener.onClick(trailers.get(position));
        }
        void bind(int position){
            trailerName.setText(trailers.get(position).getName());
            trailerSite.setText(trailers.get(position).getSite());
            trailerType.setText(trailers.get(position).getType());
        }
    }
}
