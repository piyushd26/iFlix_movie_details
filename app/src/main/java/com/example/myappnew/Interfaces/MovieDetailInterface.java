package com.example.myappnew.Interfaces;

import com.example.myappnew.Callback.MovieDetailCallback;
import com.example.myappnew.pojo.ProductionCompany;

import java.util.List;

public interface MovieDetailInterface extends BaseInteface{

    public  void  moviedetail(MovieDetailCallback movieDetailCallback, List<ProductionCompany> productionCompany);


}
