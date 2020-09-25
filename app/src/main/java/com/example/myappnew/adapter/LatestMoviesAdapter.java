package com.example.myappnew.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappnew.Activities.LogedInActivity;
import com.example.myappnew.Callback.LatestMoviesCallback;
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LatestMoviesAdapter extends RecyclerView.Adapter<LatestMoviesAdapter.ViewHolder> {


    List<LatestMoviesCallback> latestMovies =new ArrayList<>();
    Context context;

    public LatestMoviesAdapter(List<LatestMoviesCallback> mLatestMoviesData, Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_view_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        viewHolder.nameofmovies.setText(latestMovies.get(0).getOriginalTitle());
        String pPth = "https://image.tmdb.org/t/p/w500" ;
        Picasso.with(context).load(pPth  + latestMovies.get(i).getPosterPath()).placeholder(R.mipmap.defaultplaceholder).into(viewHolder.imageofmovies);

        viewHolder.imageofmovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailFragment movieDetailFragment=new MovieDetailFragment();
                movieDetailFragment.setData(latestMovies.get(i));

                ((LogedInActivity)context).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.container_mc,movieDetailFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return latestMovies.size();
    }

    public void updateData(List<LatestMoviesCallback> latestMoviesCallback) {
        this.latestMovies=latestMoviesCallback;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameofmovies;
        ImageView imageofmovies;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofmovies=itemView.findViewById(R.id.name);
            imageofmovies=itemView.findViewById(R.id.imageView4);
        }

    }


}
