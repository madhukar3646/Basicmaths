package com.app.childmaths;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;


public class MyApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        try
        {
            Class.forName("android.os.AsyncTask");
        }
        catch (Throwable throwable)
        {

        }
        //MobileAds.initialize(getApplicationContext(),getResources().getString(R.string.adds_app_id));
    }
}
