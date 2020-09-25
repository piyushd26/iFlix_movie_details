package com.example.myappnew.presenter;

import android.util.Log;

import com.example.myappnew.Callback.LatestMoviesCallback;
import com.example.myappnew.Interfaces.LatestMoviesInterface;
import com.example.myappnew.Interfaces.ResquestGet;
import com.example.myappnew.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LatestMoviesPresenter {


    LatestMoviesInterface latestMoviesInterface;

    String Api_Key= "790bfb0e28eba724d35c96efbf2af531";


    public LatestMoviesPresenter(LatestMoviesInterface latestMoviesInterface) {
        this.latestMoviesInterface=latestMoviesInterface;
    }

    public void getlatestmovies()
    {
        latestMoviesInterface.onFetchStart();
        Call<LatestMoviesCallback> latestMoviesCallbackCall = NetworkService.createService(ResquestGet.class).getlatestmovies(Api_Key);

        latestMoviesCallbackCall.enqueue(new Callback<LatestMoviesCallback>() {
            @Override
            public void onResponse(Call<LatestMoviesCallback> call, Response<LatestMoviesCallback> response) {
                latestMoviesInterface.onFetchComplete();
                latestMoviesInterface.latestmovies(response.body());

            }

            @Override
            public void onFailure(Call<LatestMoviesCallback> call, Throwable t) {
                latestMoviesInterface.onFetchFailure("latestMoviesPresenter"+t.getMessage());
                Log.i("latestMoviesPresenter", t.getMessage());
            }
        });
    }
}
