package com.example.myappnew.presenter;

import android.util.Log;

import com.example.myappnew.Callback.SimilarMoviesCallback;
import com.example.myappnew.Fragment.MovieDetailFragment;
import com.example.myappnew.Interfaces.ResquestGet;
import com.example.myappnew.Interfaces.SimilarMoviesInterface;
import com.example.myappnew.Utility;
import com.example.myappnew.service.NetworkService;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimilarMoviesPresenter {
    SimilarMoviesInterface similarMoviesInterface;
    String api_key = "790bfb0e28eba724d35c96efbf2af531" ;
    public SimilarMoviesPresenter(SimilarMoviesInterface similarMoviesInterface) {
        this.similarMoviesInterface=similarMoviesInterface;
    }

 public void getsimilarmoviespresenter(int page,int id){
        similarMoviesInterface.onFetchStart();
     Call<SimilarMoviesCallback> similarMoviesCallbackCall = NetworkService.createService(ResquestGet.class).getsimilarmovies(id, Utility.API_KEY,page);
     similarMoviesCallbackCall.enqueue(new Callback<SimilarMoviesCallback>() {
         @Override
         public void onResponse(Call<SimilarMoviesCallback> call, Response<SimilarMoviesCallback> response) {
             similarMoviesInterface.onFetchComplete();
             similarMoviesInterface.similarmovies(Collections.singletonList(response.body()),response.body().getResults());
         }

         @Override
         public void onFailure(Call<SimilarMoviesCallback> call, Throwable t) {
             similarMoviesInterface.onFetchFailure("similarMoviesPresenter"+t.getMessage());
             Log.i("similarMoviesPresenter", t.getMessage());

         }
     });
 }
}
