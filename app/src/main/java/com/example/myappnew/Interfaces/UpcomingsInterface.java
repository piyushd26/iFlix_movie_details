package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.Result;
import com.example.myappnew.Callback.UpcomingCallback;
import com.example.myappnew.pojo.Results_Upcomings;

import java.util.List;

public interface UpcomingsInterface extends BaseInteface {
    public void upcomingsdata(UpcomingCallback upcomingCallback, List<Result> results);
}
