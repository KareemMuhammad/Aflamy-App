package com.oikm.a100.popularmoviesappstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oikm.a100.popularmoviesappstage1.Model.Movie;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Movie[] mMovie ;
    private final MovieAdapterClickListener clickListener;

    public MovieAdapter(Movie[] movies ,MovieAdapterClickListener clickListener){
        this.clickListener = clickListener;
        this.mMovie = movies;
    }

    public void setData(Movie[] movieData) {
        mMovie = movieData;
    }

    public interface MovieAdapterClickListener{
        void onClick(int moviePosition);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutID = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;
        View view = inflater.inflate(layoutID,viewGroup,shouldAttach);
        return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView posterImage ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage =  itemView.findViewById(R.id.main_image_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          int adapterPosition = getAdapterPosition();
          clickListener.onClick(adapterPosition);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

          String moviePoster = mMovie[i].getPoster();
          Picasso.get()
                  .load(moviePoster)
                  .placeholder(R.drawable.loading)
                  .error(R.drawable.error)
                  .into(viewHolder.posterImage);
    }

    @Override
    public int getItemCount() {
        if (mMovie == null) {
            return 0;
        }
        return mMovie.length;
    }


}
