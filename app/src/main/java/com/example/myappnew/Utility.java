package com.example.myappnew;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class Utility extends AppCompatActivity {

    public static final String BASEURL="https://api.themoviedb.org/3/movie/";
    public static final String API_KEY="790bfb0e28eba724d35c96efbf2af531";
    public static final String Base_url_Images="https://image.tmdb.org/t/p/w500/";
    public static final String Base_url_NowPlaying="https://api.themoviedb.org/3/movie/now_playing";
    public static final String BaseUrlForSearchMovies = "https://api.themoviedb.org/3/search/";
    public static final String PREFERENCES = "Prefs";
     String mQuery="mQuery";

    public static void saveSP_Userdata(String nName, String mEmail, String mPassword, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", nName);
        editor.putString("email",mEmail);
        editor.apply();
    }
    public String loadSP_UserData_Username(Context context)
    {
        SharedPreferences sharedPreferences =context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "profilename");

        return name;
    }
    public String loadSP_Userdata_UserEmailID(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES,context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","profileEmail");
        return email;
    }


    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    public static boolean isInternetIsConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        } else {
            // not connected to the internet
            return false;
        }
        return false;
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }



}
