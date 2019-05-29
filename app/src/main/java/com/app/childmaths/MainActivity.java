package com.app.childmaths;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AppsParkAdsAdapter.OnAppClickListener{

    private RelativeLayout layout_add,layout_sub,layout_mul,layout_div,layout_tables,layout_lessthangreaterthan,layout_alphabets,layout_numbers;
    private ConnectionDetector cd;
    private boolean isInternetPresent = false;
    private AdView adviewlayout;

    private RelativeLayout more_apps_lay_out;
    private RecyclerView more_app_recycler_view;
    private Dialog dialog;
    private AppsParkAdsAdapter appsParkAdsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init()
    {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        adviewlayout = (AdView) findViewById(R.id.banner);

        dialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.servicecall_loading);
        dialog.setCancelable(false);

        more_apps_lay_out=(RelativeLayout)findViewById(R.id.more_apps_lay_out);
        more_apps_lay_out.setVisibility(View.GONE);
        more_app_recycler_view=(RecyclerView)findViewById(R.id.more_app_recycler_view);
        more_app_recycler_view.setNestedScrollingEnabled(false);
        more_app_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select Your Topic");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });

        layout_add=(RelativeLayout)findViewById(R.id.layout_add);
        layout_sub=(RelativeLayout)findViewById(R.id.layout_sub);
        layout_mul=(RelativeLayout)findViewById(R.id.layout_mul);
        layout_div=(RelativeLayout)findViewById(R.id.layout_div);
        layout_tables=(RelativeLayout)findViewById(R.id.layout_tables);
        layout_lessthangreaterthan=(RelativeLayout)findViewById(R.id.layout_lessthangreaterthan);
        layout_alphabets=(RelativeLayout)findViewById(R.id.layout_alphabets);
        layout_numbers=(RelativeLayout)findViewById(R.id.layout_numbers);

        layout_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayLevelDialog(Utils.ADDITION);
            }
        });

        layout_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayLevelDialog(Utils.SUBSTRACTION);
            }
        });


        layout_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayLevelDialog(Utils.MULTIPLICATION);
            }
        });

        layout_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayLevelDialog(Utils.DIVISION);
            }
        });

        layout_tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,TablesActivity.class);
                startActivity(intent);
            }
        });

        layout_lessthangreaterthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                displayLevelDialog("LESS THAN AND GREATER THAN");
            }
        });

        layout_alphabets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,AlphabetsActivity.class);
                intent.putExtra(Utils.SUBJECT,Utils.ALPHABETS);
                startActivity(intent);
            }
        });

        layout_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,AlphabetsActivity.class);
                intent.putExtra(Utils.SUBJECT,Utils.NUMBERS);
                startActivity(intent);
            }
        });

        if (isInternetPresent) {
            getPlaystoreApps();
        }
    }

    public void getPlaystoreApps(){
        Call<PlaystoreappslistingResponse> call= RetrofitApis.Factory.create(MainActivity.this).getAppsList();
        dialog.show();
        call.enqueue(new Callback<PlaystoreappslistingResponse>() {
            @Override
            public void onResponse(Call<PlaystoreappslistingResponse> call, Response<PlaystoreappslistingResponse> response) {
                if(dialog!=null)
                    dialog.dismiss();
                if(response.isSuccessful()){
                    PlaystoreappslistingResponse playstoreappslistingResponse=response.body();
                    if(playstoreappslistingResponse!=null)
                    {
                        List<App> appslist=playstoreappslistingResponse.getApps();
                        appsParkAdsAdapter=new AppsParkAdsAdapter(MainActivity.this,appslist);
                        appsParkAdsAdapter.setOnAppClickListener(MainActivity.this);
                        more_app_recycler_view.setAdapter(appsParkAdsAdapter);
                        more_apps_lay_out.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaystoreappslistingResponse> call, Throwable t) {
                if(dialog!=null)
                    dialog.dismiss();
            }
        });
    }
    private void displayLevelDialog(final String subject)
    {
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_levels);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        RelativeLayout layout_leve1,layout_leve2,layout_leve3,layout_leve4;
        layout_leve1=(RelativeLayout) dialog.findViewById(R.id.layout_level1);
        layout_leve2=(RelativeLayout) dialog.findViewById(R.id.layout_level2);
        layout_leve3=(RelativeLayout) dialog.findViewById(R.id.layout_level3);
        layout_leve4=(RelativeLayout) dialog.findViewById(R.id.layout_level4);

        layout_leve1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if(subject.equalsIgnoreCase("LESS THAN AND GREATER THAN"))
                    gotoLessthanGreaterthanScreen(Utils.LEVEL1);
                else
                   gotoTestScreen(subject,Utils.LEVEL1);
            }
        });

        layout_leve2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if(subject.equalsIgnoreCase("LESS THAN AND GREATER THAN"))
                    gotoLessthanGreaterthanScreen(Utils.LEVEL2);
                else
                gotoTestScreen(subject,Utils.LEVEL2);

            }
        });

        layout_leve3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if(subject.equalsIgnoreCase("LESS THAN AND GREATER THAN"))
                    gotoLessthanGreaterthanScreen(Utils.LEVEL3);
                else
                gotoTestScreen(subject,Utils.LEVEL3);
            }
        });

        layout_leve4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                if(subject.equalsIgnoreCase("LESS THAN AND GREATER THAN"))
                    gotoLessthanGreaterthanScreen(Utils.LEVEL4);
                else
                gotoTestScreen(subject,Utils.LEVEL4);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    }

    private void displayExitDialog()
    {
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exitlayout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        RelativeLayout layout_rateus,layout_cancel,layout_exit,layout_share;
        layout_share=(RelativeLayout)dialog.findViewById(R.id.layout_share);
        layout_rateus=(RelativeLayout) dialog.findViewById(R.id.layout_rateus);
        layout_cancel=(RelativeLayout) dialog.findViewById(R.id.layout_cancel);
        layout_exit=(RelativeLayout) dialog.findViewById(R.id.layout_exit);

        layout_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(cd.isConnectingToInternet())
                     startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.app.childmaths")));
                    else
                        Toast.makeText(MainActivity.this,"You've no internet connection. Please rate us after connected to internet.",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("rate us error",""+e.getMessage());
                }
            }
        });

        layout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Child Maths");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.app.childmaths\n\n";
                share.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(share, "choose one"));
            }
        });

        layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        layout_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    }


    private void gotoTestScreen(String subject,String level)
    {
        Intent intent=new Intent(MainActivity.this,TestActivity.class);
        intent.putExtra(Utils.SUBJECT,subject);
        intent.putExtra(Utils.LEVEL,level);
        startActivity(intent);
    }

    private void gotoLessthanGreaterthanScreen(String level)
    {
        Intent intent=new Intent(MainActivity.this,LessthanGreaterThanTest.class);
        intent.putExtra(Utils.LEVEL,level);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sharemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Child Maths");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.app.childmaths\n\n";
                share.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(share, "Share this App"));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
    public void onBackPressed() {
        displayExitDialog();
    }

    @Override
    public void onAppClick(App app) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(app.getAppurl())));
    }
}
