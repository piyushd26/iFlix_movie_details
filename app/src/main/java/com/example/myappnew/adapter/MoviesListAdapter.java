package com.example.myappnew.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappnew.Activities.LogedInActivity;
import com.example.myappnew.Callback.MoviesListCallback;
import com.example.myappnew.Callback.Result;
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.R;
import com.example.myappnew.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {


    CoordinatorLayout framelayout;
    FragmentManager fragmentManager;
    List<Result> mResultData = new ArrayList<>();

    List<Result> moviesListCallbacks = new ArrayList<>();
    Context mContext;

    public MoviesListAdapter(ArrayList<Result> resultData, List<Result> moviesListData, Context context, FragmentManager fragmentManager) {

        this.mResultData = moviesListData;
        this.moviesListCallbacks=moviesListCallbacks;
        this.mContext=context;

        this.fragmentManager=fragmentManager;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_view_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


       viewHolder.NameofMovies.setText(mResultData.get(i).getOriginalTitle());
       String posterImages= mResultData.get(i).getPosterPath();
       String posterurl= Utility.Base_url_Images+posterImages;
       Picasso.with(mContext.getApplicationContext()).load(posterurl).placeholder(R.mipmap.defaultplaceholder).into(viewHolder.ImageofMovies);
       viewHolder.RatingofMovie.setText(String.valueOf(mResultData.get(i).getVoteAverage()));
        viewHolder.YearofMovies.setText( mResultData.get(i).getReleaseDate());

        viewHolder.DetailFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieDetailFragment movieDetails =new MovieDetailFragment();
                movieDetails.setData(mResultData.get(i));

            ((LogedInActivity)mContext).getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                                        .replace(R.id.container_loggedin,movieDetails).addToBackStack(null).commit();


            }
        });

    }


    @Override
    public int getItemCount() {
        return mResultData.size();
    }


    public void updateData(List<Result> moviesListCallbacks) {
        this.mResultData = moviesListCallbacks;
        this.notifyDataSetChanged();
    }


    public void updateResultData(ArrayList<Result> result_) {
        mResultData = result_;
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NameofMovies;
        ImageView ImageofMovies;
        TextView RatingofMovie;
        TextView YearofMovies;
        ImageView DetailFragment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameofMovies = itemView.findViewById(R.id.name);
            ImageofMovies = itemView.findViewById(R.id.imageView4);
            RatingofMovie = itemView.findViewById(R.id.ratingt);
            YearofMovies = itemView.findViewById(R.id.yearodmovie);
            DetailFragment=itemView.findViewById(R.id.imageView4);
        }
    }
}
