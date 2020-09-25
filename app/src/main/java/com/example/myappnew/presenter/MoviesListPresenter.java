package com.example.myappnew.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myappnew.Callback.MoviesListCallback;
import com.example.myappnew.Callback.Result;
import com.example.myappnew.Interfaces.MoviesInterface;
import com.example.myappnew.Interfaces.ResquestGet;
import com.example.myappnew.Interfaces.ResultsInterface;
import com.example.myappnew.Utility;
import com.example.myappnew.adapter.MoviesListAdapter;
import com.example.myappnew.service.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListPresenter {

    NetworkService networkService;

    MoviesInterface moviesInterface;

    ResultsInterface resultsInterface;

    List<Result> results= new ArrayList<>();

    String api_key="790bfb0e28eba724d35c96efbf2af531";


    public MoviesListPresenter(MoviesInterface moviesInterface,ResultsInterface resultsInterface) {
        this.moviesInterface = moviesInterface;
        this.resultsInterface=resultsInterface;

    }
    public void getNowPlayingmoviesforlist(int page) {
        moviesInterface.onFetchStart();
        Call<MoviesListCallback> moviesListCallbackCall = networkService.createService(ResquestGet.class)
                .getMoviesNowPlaying(Utility.API_KEY,page);

        moviesListCallbackCall.enqueue(new Callback<MoviesListCallback>() {
            @Override
            public void onResponse(Call<MoviesListCallback> call, Response<MoviesListCallback> response) {
                moviesInterface.onmovies(response.body(),response.body().getResults());
                results=response.body().getResults();
                moviesInterface.onFetchComplete();

            }

            @Override
            public void onFailure(Call<MoviesListCallback> call, Throwable t) {
                moviesInterface.onFetchFailure(t.getMessage());
            }
        });


}


    public void getmoviesforQueryList(String query) {
        String BaseUrlForSearchMovies = "https://api.themoviedb.org/3/search/";
        moviesInterface.onFetchStart();
        Call<MoviesListCallback> moviesListCallbackCall = NetworkService
                .createService(ResquestGet.class)
                .getMoviesByQuery(api_key,query);

        moviesListCallbackCall.enqueue(new Callback<MoviesListCallback>() {
            @Override
            public void onResponse(Call<MoviesListCallback> call, Response<MoviesListCallback> response) {
                if(response.isSuccessful())
                {
                    moviesInterface.onmovies(response.body(),response.body().getResults());
                    List<Result> results=response.body().getResults();
                    resultsInterface.resultinterface(results);
                    moviesInterface.onFetchComplete();
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<MoviesListCallback> call, Throwable t) {
                moviesInterface.onFetchFailure("MoviesListPresenter(query)"+t.getMessage());
                Log.i("MoviesListPresenter(query)", t.getMessage());
            }
        });
    }
}
