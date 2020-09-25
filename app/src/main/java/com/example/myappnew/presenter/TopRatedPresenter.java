package com.example.myappnew.presenter;

import android.content.Context;
import android.util.Log;

import com.example.myappnew.Activities.LogedInActivity;
import com.example.myappnew.Callback.TopRatedCallback;
import com.example.myappnew.Interfaces.ResquestGet;
import com.example.myappnew.Interfaces.TopRatedInterface;
import com.example.myappnew.Utility;
import com.example.myappnew.pojo.Results_TopRated;
import com.example.myappnew.service.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedPresenter {
    TopRatedInterface topratedInterface;
    private String api_key = "790bfb0e28eba724d35c96efbf2af531";

    public TopRatedPresenter(TopRatedInterface topRatedInterface) {
        this.topratedInterface=topRatedInterface;
    }

    public void getoprated(int page) {

        topratedInterface.onFetchStart();
        Call<TopRatedCallback> topRatedCallbackCall = NetworkService.createService(ResquestGet.class).getTopRatedData(Utility.API_KEY,page);
        topRatedCallbackCall.enqueue(new Callback<TopRatedCallback>() {
            @Override
            public void onResponse(Call<TopRatedCallback> call, Response<TopRatedCallback> response) {
                if(response.isSuccessful()) {
                    topratedInterface.onFetchComplete();
                    topratedInterface.toprated(response.body(),response.body().getResults());

                }
            }

            @Override
            public void onFailure(Call<TopRatedCallback> call, Throwable t) {
                topratedInterface.onFetchFailure("topRatedPresenter"+t.getMessage());
                Log.i("topRatedPresenter", t.getMessage());
            }
        });

    }
}
