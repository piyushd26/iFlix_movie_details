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
import com.example.myappnew.Utility;
import com.example.myappnew.pojo.Results_Popular;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder> {
    List<Result> popularmoviesData =new ArrayList<>();
    Context context;
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private boolean isLoadingAdded = false;

    public PopularMoviesAdapter(List<Result> mPMovies, Context context) {
        this.popularmoviesData= mPMovies;
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
        viewHolder.nameofMovies.setText(popularmoviesData.get(i).getOriginalTitle());
        String posterImages=popularmoviesData.get(i).getPosterPath();
        String posterurl= Utility.Base_url_Images+posterImages;
        Picasso.with(context.getApplicationContext()).load(posterurl).placeholder(R.mipmap.defaultplaceholder).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailFragment movieDetailFragment =new MovieDetailFragment();
                movieDetailFragment.setData(popularmoviesData.get(i));
                ((LogedInActivity)context).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.container_loggedin,movieDetailFragment).addToBackStack(null).commit();
            }
        });
        viewHolder.year.setText(popularmoviesData.get(i).getReleaseDate());
        viewHolder.rate.setText(String.valueOf(popularmoviesData.get(i).getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return popularmoviesData.size();
    }

    public void updateData(List<Result> mPmovies) {
        this.popularmoviesData= mPmovies;
        this.notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameofMovies;
        ImageView imageView;
        TextView year;
        TextView rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofMovies=itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.imageView4);
            year=itemView.findViewById(R.id.yearodmovie);
            rate=itemView.findViewById(R.id.ratingt);
        }
    }

    public void addItems(List<Result> postItems) {
        popularmoviesData.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;
        popularmoviesData.add(new Result());
        notifyItemInserted(popularmoviesData.size() - 1);
    }
    public void removeLoading() {
        isLoaderVisible = false;
        int position = popularmoviesData.size() - 1;
        Result item = getItem(position);
        if (item != null) {
            popularmoviesData.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        popularmoviesData.clear();
        notifyDataSetChanged();
    }
    Result getItem(int position) {
        return popularmoviesData.get(position);
    }

}
