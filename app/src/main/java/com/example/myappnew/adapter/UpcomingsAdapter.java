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
import com.example.myappnew.Callback.Result;
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.R;
import com.example.myappnew.pojo.Results_TopRated;
import com.example.myappnew.pojo.Results_Upcomings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UpcomingsAdapter extends RecyclerView.Adapter<UpcomingsAdapter.ViewHolder> {

    private List<Result> mUpcomingsData = new ArrayList<>();
    Context context;

    public UpcomingsAdapter(List<Result> results_upcomings, Context context) {
        this.mUpcomingsData=results_upcomings;
        this.context=context;
    }

    public void updateData(List<Result> results_upcomings) {
        this.mUpcomingsData=results_upcomings;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_view_card,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.nameofMvoes.setText(mUpcomingsData.get(i).getOriginalTitle());
        String path = "https://image.tmdb.org/t/p/w500" ;
        Picasso.with(context).load(path+mUpcomingsData.get(i).getPosterPath()).placeholder(R.mipmap.defaultplaceholder).into(viewHolder.imageofMovies);
        viewHolder.year.setText(mUpcomingsData.get(i).getReleaseDate());
        viewHolder.ratings.setText(String.valueOf(mUpcomingsData.get(i).getVoteAverage()));
        viewHolder.imageofMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailFragment movieDetails =new MovieDetailFragment();
                movieDetails.setData(mUpcomingsData.get(i));
                ((LogedInActivity)context).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.container_loggedin,movieDetails).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUpcomingsData.size();
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
