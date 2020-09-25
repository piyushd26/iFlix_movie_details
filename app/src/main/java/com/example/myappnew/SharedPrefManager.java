package com.example.myappnew;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SHARED_PREF_NAME = "fcmsharedprefdemo";
    public static final String KEY_ACCESS_TOKEN = "token";
    public  static  Context mcontext;
    public  static SharedPrefManager mInstance;
    public SharedPrefManager(Context context)
    {

        this.mcontext=context;

    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean storetoken(String token)
    {
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor   editor=  sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN,token);
        editor.apply();
        return true;

    }

    public String gettoken()
    {
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);
    }
}
