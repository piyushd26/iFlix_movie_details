package com.example.myappnew.Interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface LoginInterface {
    @POST("helloworld.txt")
    Call<ResponseBody> loginCall();
}
