package com.example.myappnew;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class ListingPaginationOnScrollListner extends RecyclerView.OnScrollListener
{

    LinearLayoutManager layoutManager;

    public ListingPaginationOnScrollListner(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount=layoutManager.getChildCount();
        int totalItemCount=layoutManager.getItemCount()-2;
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if(!isLoading() && isLastPage())
        {

            if(visibleItemCount+firstVisibleItemPosition>=totalItemCount && firstVisibleItemPosition>=0)
            {

                loadMoreItems();

            }


        }
    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();





}