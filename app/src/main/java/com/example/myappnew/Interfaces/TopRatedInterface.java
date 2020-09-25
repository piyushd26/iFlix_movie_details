package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.TopRatedCallback;
import com.example.myappnew.pojo.Results_TopRated;

import java.util.List;

public interface TopRatedInterface extends BaseInteface{
    public  void toprated(TopRatedCallback topRatedCallback, List<Results_TopRated> results_topRateds);
}
