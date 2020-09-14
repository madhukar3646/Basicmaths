package com.app.childmaths;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdView;

public class TablesActivity extends AppCompatActivity {

    private ImageView btn_inc,btn_dec;
    private TextView tv_tableresult;
    private int screenWidth,screenHeight;
    private String result="";
    private int count=1;

    private ConnectionDetector cd;
    private boolean isInternetPresent = false;
    private AdView adviewlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        init();
    }

    private void init()
    {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        adviewlayout = (AdView) findViewById(R.id.banner);

        count=1;
        DisplayMetrics metrics=this.getResources().getDisplayMetrics();
        screenWidth=metrics.widthPixels;
        screenHeight=metrics.heightPixels;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tables");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_inc=(ImageView)findViewById(R.id.btn_inc);
        btn_inc.getLayoutParams().width=screenWidth/7;
        btn_inc.getLayoutParams().height=screenWidth/7;

        btn_dec=(ImageView)findViewById(R.id.btn_dec);
        btn_dec.getLayoutParams().width=screenWidth/7;
        btn_dec.getLayoutParams().height=screenWidth/7;

        tv_tableresult=(TextView)findViewById(R.id.tv_tableresult);

        btn_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count=count+1;
               displayTable(count);
            }
        });

        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count=count-1;
                if(count==0)
                    count=1;
                displayTable(count);
            }
        });

        displayTable(count);
    }

    private void displayTable(int count)
    {
        result="";
        for(int i=1;i<=10;i++)
        {
            if(i==10)
               result = result + "" + count + " X " + i + "  =  " + (count * i) + "\n";
            else
                result = result + "" + count + " X " + i + "    =  " + (count * i) + "\n";
        }
        tv_tableresult.setText(result);
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
                startActivity(Intent.createChooser(share, "choose one"));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
