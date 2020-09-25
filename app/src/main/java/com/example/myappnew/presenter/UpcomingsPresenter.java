package com.example.myappnew.presenter;

import com.example.myappnew.Callback.UpcomingCallback;
import com.example.myappnew.Interfaces.ResquestGet;
import com.example.myappnew.Interfaces.UpcomingsInterface;
import com.example.myappnew.Utility;
import com.example.myappnew.service.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingsPresenter {
    UpcomingsInterface  upcomingsInterface;
    String api_key = "790bfb0e28eba724d35c96efbf2af531" ;
    NetworkService networkService;

    public UpcomingsPresenter(UpcomingsInterface upcomingsInterface) {
        this.upcomingsInterface=upcomingsInterface;
    }


    public void getUpcomingsPresenter(int page) {
        upcomingsInterface.onFetchStart();
        Call<UpcomingCallback> upcomingCallbackCall = NetworkService.createService(ResquestGet.class).getupcominmovies(Utility.API_KEY,page);
        upcomingCallbackCall.enqueue(new Callback<UpcomingCallback>() {
            @Override
            public void onResponse(Call<UpcomingCallback> call, Response<UpcomingCallback> response) {
                if(response.isSuccessful())
                {
                    upcomingsInterface.onFetchComplete();
                upcomingsInterface.upcomingsdata(response.body(),response.body().getResults());
            }
            }

            @Override
            public void onFailure(Call<UpcomingCallback> call, Throwable t) {
                upcomingsInterface.onFetchFailure(t.getMessage());
            }
        });

    }
}
