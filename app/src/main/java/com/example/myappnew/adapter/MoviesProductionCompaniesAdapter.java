package com.example.myappnew.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappnew.Callback.MovieDetailCallback;
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.R;
import com.example.myappnew.pojo.ProductionCompany;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesProductionCompaniesAdapter extends RecyclerView.Adapter<MoviesProductionCompaniesAdapter.ViewHolder> {

   List<ProductionCompany> moviesproductioncompaniesData;

   Context con;

    public MoviesProductionCompaniesAdapter (List<ProductionCompany> movieDetailCallbacks, Context context){

        this.moviesproductioncompaniesData=movieDetailCallbacks;

        this.con=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(con)
                .inflate(R.layout.movies_production_companies_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.name.setText(moviesproductioncompaniesData.get(i).getName());

        String path = "https://image.tmdb.org/t/p/w500";
           Picasso.with(con)
                   .load(path+moviesproductioncompaniesData.get(i).getLogoPath())
                   .placeholder(R.mipmap.defaultplaceholder)
                   .into(viewHolder.moviesImages);

         }

    @Override
    public int getItemCount() {
        return moviesproductioncompaniesData.size();
    }

    public void updateData(List<ProductionCompany> productionCompanies) {
        moviesproductioncompaniesData=productionCompanies;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView .ViewHolder {
        ImageView moviesImages;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesImages=itemView.findViewById(R.id.mdp_itemimage);
            name=itemView.findViewById(R.id.mdp_itemtext);
        }
    }
}
