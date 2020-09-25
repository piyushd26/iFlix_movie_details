package com.example.myappnew.Interfaces;

import java.security.PublicKey;

interface BaseInteface {
    public void onFetchStart();

    public void onFetchFailure(String messege);

    public void onFetchComplete();

}
