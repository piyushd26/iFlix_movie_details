package com.example.myappnew.Callback;

import java.io.Serializable;
import java.util.List;

import com.example.myappnew.pojo.Results_TopRated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopRatedCallback implements Serializable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Results_TopRated> results = null;
    private final static long serialVersionUID = -3310110390578162777L;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Results_TopRated> getResults() {
        return results;
    }

    public void setResults(List<Results_TopRated> results) {
        this.results = results;
    }
}