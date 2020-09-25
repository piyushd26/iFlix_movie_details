package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.PopularMoviesCallback;
import com.example.myappnew.Callback.Result;
import com.example.myappnew.pojo.Results_Popular;

import java.util.List;

public interface PopularMoviesInterface extends BaseInteface {

    public void popularmovies(PopularMoviesCallback popularMovies, List<Result> result);
}
