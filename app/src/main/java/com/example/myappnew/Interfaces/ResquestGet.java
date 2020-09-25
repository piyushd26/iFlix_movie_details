package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.LatestMoviesCallback;
import com.example.myappnew.Callback.MovieDetailCallback;
import com.example.myappnew.Callback.MoviesListCallback;
import com.example.myappnew.Callback.PopularMoviesCallback;
import com.example.myappnew.Callback.SimilarMoviesCallback;
import com.example.myappnew.Callback.TopRatedCallback;
import com.example.myappnew.Callback.UpcomingCallback;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ResquestGet {


   //AVENGER     https://api.themoviedb.org/3/search/movie?api_key=790bfb0e28eba724d35c96efbf2af531&query=
   //FOR IMAGES  https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
   //NOWPLAYING  https://api.themoviedb.org/3/movie/now_playing?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US&page=1
 //GET DETAILS   https://api.themoviedb.org/3/movie/530915?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US
 //LATEST        https://api.themoviedb.org/3/movie/latest?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US
   //POPULAR     https://api.themoviedb.org/3/movie/popular?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US&page=1
 //TOP RATED     https://api.themoviedb.org/3/movie/top_rated?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US&page=1
//UPCOMINGS      https://api.themoviedb.org/3/movie/upcoming?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US&page=1
 //SIMILAR MOVIES https://api.themoviedb.org/3/movie/530915/similar?api_key=790bfb0e28eba724d35c96efbf2af531&language=en-US&page=1
    @GET("movie/now_playing?")
    Call<MoviesListCallback> getMoviesNowPlaying(
            @Query("api_key") String api_key,
             @Query("page") int page

    );

    @GET("search/movie?")
    Call<MoviesListCallback> getMoviesByQuery(
            @Query("api_key") String api_key,
            @Query("query") String query

    );

    @GET("movie/{movie_id}")
    Call<MovieDetailCallback> getUsers(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key

    );

    @GET ("movie/latest?")
    Call<LatestMoviesCallback> getlatestmovies(
         @Query("api_key") String api_key
    );

    @GET ("movie/popular?")
    Call<PopularMoviesCallback> getPopularMoviesData(
         @Query("api_key") String api_key,
         @Query( "page") int page
    );

    @GET ("movie/top_rated?")
    Call<TopRatedCallback> getTopRatedData(
         @Query("api_key") String top_rated,
         @Query("page") int page
    );




 @GET ("movie/{movie_id}")
 Call<SimilarMoviesCallback> getsimilarmovies(
         @Path("movie_id") int movie_id,
         @Query("api_key") String api_key,
         @Query("page") int page
 );

@GET ("movie/upcoming?")
 Call<UpcomingCallback> getupcominmovies(
        @Query("api_key") String api_key,
        @Query("page") int page
);
}
