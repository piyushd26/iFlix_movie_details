package com.example.myappnew.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myappnew.Callback.Result;
import com.example.myappnew.R;

import java.util.ArrayList;
import java.util.List;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.ViewHolder> {
   List<String> list =new ArrayList<>();

    Context context;
    List<Result> moviesData = new ArrayList<>();

    public SearchViewAdapter(List<Result> moviesListData, Context context) {
        this.moviesData=moviesListData;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.movies_list_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nameofmovies.setText(moviesData.get(i).getOriginalTitle());

    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public void updateData(List<Result> moviesListData) {
        this.moviesData=moviesListData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameofmovies;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameofmovies=itemView.findViewById(R.id.name_tv_);
        }
    }
}
