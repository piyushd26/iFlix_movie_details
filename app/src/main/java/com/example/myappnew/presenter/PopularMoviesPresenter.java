package com.example.myappnew.presenter;

import android.util.Log;

import com.example.myappnew.Callback.PopularMoviesCallback;
import com.example.myappnew.Interfaces.PopularMoviesInterface;
import com.example.myappnew.Interfaces.ResquestGet;

import com.example.myappnew.Utility;
import com.example.myappnew.service.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesPresenter  {
    PopularMoviesInterface popularMoviesInterface;

    List<PopularMoviesCallback>  results_popular = new ArrayList<>();

    String api_key= "790bfb0e28eba724d35c96efbf2af531";

    NetworkService networkService;

    public PopularMoviesPresenter(PopularMoviesInterface popularMoviesInterface) {
        this.popularMoviesInterface=popularMoviesInterface;
    }


    public void getPopularMoviesData(int page){


        popularMoviesInterface.onFetchStart();
        Call<PopularMoviesCallback> popularmoviesCall  =  NetworkService.createService(ResquestGet.class).getPopularMoviesData(Utility.API_KEY,page);
        popularmoviesCall.enqueue(new Callback<PopularMoviesCallback>() {
            @Override
            public void onResponse(Call<PopularMoviesCallback> call, Response<PopularMoviesCallback> response) {
                if (response.isSuccessful()) {
                    popularMoviesInterface.onFetchComplete();
                    popularMoviesInterface.popularmovies(response.body(),response.body().getResults());
                }
            }
            @Override
            public void onFailure(Call<PopularMoviesCallback> call, Throwable t) {
                popularMoviesInterface.onFetchFailure("PopularMoviesPresenter"+t.getMessage());
                Log.i("PopularMoviesPresenter", t.getMessage());
            }
        });

    }
}
