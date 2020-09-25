package com.example.myappnew.presenter;

import android.util.Log;

import com.example.myappnew.Interfaces.LoginInterface;
import com.example.myappnew.service.NetworkService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    NetworkService networkService;
    LoginInterface loginInterface;

    public void getLogin() {

        Call<ResponseBody> responseBodyCall = networkService.createService(LoginInterface.class).loginCall();
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("response", "onResponse: success");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("response", "onResponse: failedd");
            }
        });
    }

}
