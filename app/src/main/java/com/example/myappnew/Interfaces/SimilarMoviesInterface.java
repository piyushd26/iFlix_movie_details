package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.Result;
import com.example.myappnew.Callback.SimilarMoviesCallback;

import java.util.List;

public interface SimilarMoviesInterface extends BaseInteface{
    public void similarmovies(List<SimilarMoviesCallback> similarMoviesCallback, List<Result> results);
}
