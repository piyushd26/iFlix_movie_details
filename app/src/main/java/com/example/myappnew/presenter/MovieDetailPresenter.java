package com.example.myappnew.presenter;

import android.util.Log;

import com.example.myappnew.Callback.MovieDetailCallback;
import com.example.myappnew.Interfaces.MovieDetailInterface;
import com.example.myappnew.Interfaces.ResquestGet;
import com.example.myappnew.Utility;
import com.example.myappnew.pojo.ProductionCompany;
import com.example.myappnew.service.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailPresenter {

    MovieDetailInterface movieDetailInterface;

    NetworkService Network;

    String API_key="790bfb0e28eba724d35c96efbf2af531";

    List<ProductionCompany> productionCompanies;

    public MovieDetailPresenter(MovieDetailInterface moviedetailData) {
        this.movieDetailInterface=moviedetailData;
    }

    public void getMovieDetailData(int movieId)
    {
        int id=movieId;
        movieDetailInterface.onFetchStart();

        Call<MovieDetailCallback> movieDetailCallbackCall = NetworkService.createService(ResquestGet.class)
                .getUsers(id, Utility.API_KEY);
        movieDetailCallbackCall.enqueue(new Callback<MovieDetailCallback>() {
            @Override
            public void onResponse(Call<MovieDetailCallback> call, Response<MovieDetailCallback> response) {
                if(response.isSuccessful())
                {
                    movieDetailInterface.onFetchComplete();
                    movieDetailInterface.moviedetail(response.body(),response.body().getProductionCompanies());
                   productionCompanies=response.body().getProductionCompanies();


                }
            }

            @Override
            public void onFailure(Call<MovieDetailCallback> call, Throwable t) {
                Log.i("MoviesDetailPresenter", "onFailure: Call Failed"+t.getMessage());
                movieDetailInterface.onFetchFailure("MoviesDetailPresenter"+t.getMessage());
            }
        });
    }
}
