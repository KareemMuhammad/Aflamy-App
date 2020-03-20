package com.oikm.a100.popularmoviesappstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oikm.a100.popularmoviesappstage1.Model.Trailers;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<Trailers> resultList;
    private OnItemClickListener clickListener;
    private static final String TAG = DetailActivity.class.getSimpleName();

    public TrailerAdapter(List<Trailers> resultList, OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        this.resultList = resultList;
    }

    public interface OnItemClickListener{
        void onClickListener(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder viewHolder, int i) {
        String name = resultList.get(i).getName();
        Log.d(TAG,"name "+name);
     viewHolder.trailerText.setText(name);
    }
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.list_trailers;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean attachToRoot = false;
        View view = inflater.inflate(layoutId,viewGroup,attachToRoot);
        return new TrailerViewHolder(view);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       public TextView trailerText;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
          trailerText = itemView.findViewById(R.id.trailer_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onClickListener(position);
        }
    }

    @Override
    public int getItemCount() {
        if (resultList == null){
            return 0;
        }
        else {
            return resultList.size();
        }
    }
    public void setData(List<Trailers> results) {
        resultList = results;
    }
}
