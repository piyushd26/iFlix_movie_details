package com.example.myappnew.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappnew.Callback.Result;
import com.example.myappnew.Callback.SimilarMoviesCallback;
import com.example.myappnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder> {
    List<SimilarMoviesCallback> similarMoviesCallbacks =new ArrayList<>();
    Context context;
    public SimilarMoviesAdapter(List<SimilarMoviesCallback> mSimilarMoviesData, Context context) {
        this.similarMoviesCallbacks=mSimilarMoviesData;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.movies_view_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.name.setText(similarMoviesCallbacks.get(0).getResults().get(i).getOriginalTitle());
        String path = "https://image.tmdb.org/t/p/w500/";
        Picasso.with(context).load(path+similarMoviesCallbacks.get(0).getResults().get(i).getPosterPath()).placeholder(R.mipmap.defaultplaceholder).into(viewHolder.imageofcard);
    }

    @Override
    public int getItemCount() {

       return similarMoviesCallbacks.size();
    }

    public void updateData( List<SimilarMoviesCallback> similarMoviesCallbacks,List<Result> results) {
        this.similarMoviesCallbacks=similarMoviesCallbacks;

        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageofcard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            imageofcard=itemView.findViewById(R.id.imageView4);
        }
    }
}
