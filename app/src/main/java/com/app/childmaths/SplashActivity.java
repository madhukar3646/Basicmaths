package com.app.childmaths;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ConnectionDetector cd;
    private boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        try {
                new Handler().postDelayed(new Runnable() {
                    // Using handler with postDelayed called runnable run method
                    @Override
                    public void run() {
                        startintent();
                    }
                }, 2 * 1000);
        } catch (Exception e) {

        }
    }

    private void startintent() {

        Intent i = new Intent(SplashActivity.this,
                MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
