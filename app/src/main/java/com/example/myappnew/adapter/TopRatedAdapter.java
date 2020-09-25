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
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.R;
import com.example.myappnew.pojo.Results_TopRated;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {

    private List<Results_TopRated> results_topRateds = new ArrayList<>();
    Context ctn;



    public TopRatedAdapter(List<Results_TopRated> mTopratedData, Context context) {
        this.results_topRateds=mTopratedData;
        this.ctn=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctn).inflate(R.layout.movies_view_card,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.nameofMvoes.setText(results_topRateds.get(i).getOriginalTitle());
        String path = "https://image.tmdb.org/t/p/w500" ;
        Picasso.with(ctn).load(path+results_topRateds.get(i).getPosterPath()).placeholder(R.mipmap.defaultplaceholder).into(viewHolder.imageofMovies);
        viewHolder.year.setText(results_topRateds.get(i).getReleaseDate());
        viewHolder.ratings.setText(String.valueOf(results_topRateds.get(i).getVoteAverage()));
        viewHolder.imageofMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                movieDetailFragment.setData(results_topRateds.get(i));

                ((LogedInActivity)ctn).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right).replace(R.id.container_loggedin,movieDetailFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return results_topRateds.size();
    }
    public void updateData(List<Results_TopRated> mTopRatedData) {
        this.results_topRateds=mTopRatedData;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView year;
        TextView nameofMvoes;
        TextView ratings;
        ImageView imageofMovies;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofMvoes=itemView.findViewById(R.id.name);
            imageofMovies=itemView.findViewById(R.id.imageView4);
            year=itemView.findViewById(R.id.yearodmovie);
            ratings = itemView.findViewById(R.id.ratingt);
        }
    }
}
