package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.MoviesListCallback;
import com.example.myappnew.Callback.Result;

import java.util.List;


public interface MoviesInterface extends BaseInteface {

    public void onmovies(MoviesListCallback moviesListCallback, List<Result> results);
}
