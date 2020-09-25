package com.example.myappnew.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myappnew.Callback.LatestMoviesCallback;
import com.example.myappnew.Callback.MovieDetailCallback;
import com.example.myappnew.Callback.Result;
import com.example.myappnew.Callback.SimilarMoviesCallback;
import com.example.myappnew.Interfaces.LatestMoviesInterface;
import com.example.myappnew.Interfaces.MovieDetailInterface;
import com.example.myappnew.Interfaces.SimilarMoviesInterface;
import com.example.myappnew.R;
import com.example.myappnew.adapter.MoviesProductionCompaniesAdapter;
import com.example.myappnew.adapter.SimilarMoviesAdapter;
import com.example.myappnew.pojo.ProductionCompany;
import com.example.myappnew.pojo.Results_TopRated;
import com.example.myappnew.presenter.MovieDetailPresenter;
import com.example.myappnew.presenter.SimilarMoviesPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieDetailFragment extends Fragment implements MovieDetailInterface, LatestMoviesInterface, SimilarMoviesInterface {

    Result result;
    List<MovieDetailCallback> moviedetailData = new ArrayList<>();
    MovieDetailPresenter movieDetailPresenter;
    TextView nameofmovies;
    ImageView posterofmovies;
    TextView tgline;
    TextView released;
    ImageView wpofmovies;
    List<ProductionCompany> productionCompaniesData = new ArrayList<>();
    Context context;
    View view;
    ProgressBar progressBar;
    TextView rated;
    TextView overveiw;
    TextView moviesgenres;
    TextView t_line;
    TextView moviesg;
    ArrayList<String> strings = new ArrayList<>();
    RecyclerView mrRV;
    MoviesProductionCompaniesAdapter moviesProductionCompaniesAdapter;
    LatestMoviesCallback latestmoviesData;
    SimilarMoviesAdapter similarMoviesAdapter;
    SimilarMoviesPresenter similarMoviesPresenter;
    RecyclerView sm_rv;
    LinearLayoutManager linearLayoutManager;
    List<SimilarMoviesCallback> mSimilarMoviesData = new ArrayList<>();
    Results_TopRated results_topRated;

    public void setData(LatestMoviesCallback latestMoviesCallback) {
        this.latestmoviesData = latestMoviesCallback;
    }

    public void setData(Result result) {
        this.result = result;
    }

    public void setData(Results_TopRated results_topRated) {
        this.results_topRated = results_topRated;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        movieDetailPresenter = new MovieDetailPresenter(this);
        if (result != null) {
            movieDetailPresenter.getMovieDetailData(result.getId());
        } else if (results_topRated != null) {
            movieDetailPresenter.getMovieDetailData(results_topRated.getId());
        }
        posterofmovies = view.findViewById(R.id.md_poster);
        progressBar = view.findViewById(R.id.progressbar_moviedetail);
        wpofmovies = view.findViewById(R.id.backdrop);
        nameofmovies = view.findViewById(R.id.moviesname);
        tgline = view.findViewById(R.id.tgline);
        released = view.findViewById(R.id.released);
        rated = view.findViewById(R.id.rated);
        overveiw = view.findViewById(R.id.md_overview);
        moviesgenres = view.findViewById(R.id.moviesgenres);
        moviesg = view.findViewById(R.id.moviesgenresname);
        mrRV = view.findViewById(R.id.md_rv);
        sm_rv = view.findViewById(R.id.sm_rv);

        similarmovies();

        return view;
    }

    private void similarmovies() {
        sm_rv.setVisibility(View.VISIBLE);
        similarMoviesAdapter = new SimilarMoviesAdapter(mSimilarMoviesData, getContext());
        similarMoviesPresenter = new SimilarMoviesPresenter(this);
        // similarMoviesPresenter.getsimilarmoviespresenter(1,530915);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        sm_rv.setLayoutManager(linearLayoutManager);
        sm_rv.setAdapter(similarMoviesAdapter);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void moviedetail(MovieDetailCallback movieDetailCallback, List<ProductionCompany> productionCompany) {
        productionCompaniesData = productionCompany;
        moviesProductionCompaniesAdapter = new MoviesProductionCompaniesAdapter(productionCompaniesData, view.getContext());
        moviesProductionCompaniesAdapter.updateData(productionCompaniesData);
        moviedetailData = Collections.singletonList(movieDetailCallback);
        if (moviedetailData != null) {
            // https://image.tmdb.org/t/p/w500/iZf0KyrE25z1sage4SYFLCCrMi9.jpg
            progressBar.setVisibility(View.GONE);
            String path = "https://image.tmdb.org/t/p/w500";
            Picasso.with(getContext()).load(path + moviedetailData.get(0).getPosterPath()).placeholder(R.mipmap.defaultplaceholder).into(posterofmovies);
            Picasso.with(getContext()).load(path + moviedetailData.get(0).getBackdropPath()).placeholder(R.mipmap.defaultplaceholder).into(wpofmovies);
            nameofmovies.setText(moviedetailData.get(0).getOriginalTitle());
            if (moviedetailData.get(0).getTagline() != null) {
                tgline.setVisibility(View.VISIBLE);
                tgline.setText(moviedetailData.get(0).getTagline());
            }
            if (moviedetailData.get(0).getStatus() != null) {
                released.setVisibility(View.VISIBLE);
                released.setText(moviedetailData.get(0).getStatus());
            }
            if (moviedetailData.get(0).getVoteAverage() != null) {
                rated.setVisibility(View.VISIBLE);
                rated.setText(String.valueOf(moviedetailData.get(0).getVoteAverage()));
            }
            if (moviedetailData.get(0).getOverview() != null) {
                overveiw.setVisibility(View.VISIBLE);
                overveiw.setText(moviedetailData.get(0).getOverview());
            }

            //animator();

            if (moviedetailData.get(0).getGenres().size() == 3) {
                moviesgenres.setText(moviedetailData.get(0).getGenres().get(0).getName() + ","
                        + moviedetailData.get(0).getGenres().get(1).getName() + ","
                        + moviedetailData.get(0).getGenres().get(2).getName());
            } else if (moviedetailData.get(0).getGenres().size() == 2) {
                moviesgenres.setText(moviedetailData.get(0).getGenres().get(0).getName() + ","
                        + moviedetailData.get(0).getGenres().get(1).getName());
            } else if (moviedetailData.get(0).getGenres().size() == 1) {
                moviesgenres.setText(moviedetailData.get(0).getGenres().get(0).getName());
            } else if (moviedetailData.get(0).getGenres().size() == 0) {
                moviesgenres.setVisibility(View.GONE);
                moviesg.setVisibility(View.GONE);
            }

            prouctionCompaniesAdapter();
        }
    }

    private void prouctionCompaniesAdapter() {
        mrRV.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mrRV.setLayoutManager(linearLayoutManager);
        mrRV.setAdapter(moviesProductionCompaniesAdapter);
    }

    private void animator() {
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.animated_text);
        a.reset();
        rated = (TextView) view.findViewById(R.id.rated);
        rated.clearAnimation();
        rated.startAnimation(a);
    }

    @Override
    public void onFetchStart() {
        view.findViewById(R.id.progressbar_moviedetail).setVisibility(View.VISIBLE);//progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchFailure(String messege) {
        view.findViewById(R.id.progressbar_moviedetail).setVisibility(View.GONE);
    }

    @Override
    public void onFetchComplete() {
        view.findViewById(R.id.progressbar_moviedetail).setVisibility(View.GONE);
    }

    @Override
    public void latestmovies(LatestMoviesCallback latestMoviesCallback) {
        this.latestmoviesData = latestMoviesCallback;
        if (latestmoviesData != null) {
            nameofmovies.setText(latestmoviesData.getOriginalTitle());
            String path = "https://image.tmdb.org/t/p/w500";
            Picasso.with(getContext()).load(path + latestmoviesData.getPosterPath()
            ).placeholder(R.mipmap.defaultplaceholder).into(posterofmovies);
            rated.setText(latestmoviesData.getVoteAverage());

        }
    }

    @Override
    public void similarmovies(List<SimilarMoviesCallback> similarMoviesCallback, List<Result> results) {
        this.mSimilarMoviesData = similarMoviesCallback;

        similarMoviesAdapter.updateData(similarMoviesCallback, results);

    }


}
