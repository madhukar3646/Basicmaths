package com.app.childmaths;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

public class AlphabetsActivity extends AppCompatActivity {

    private RelativeLayout btn_clear;
    private TextView tv_originaltxt;
    private ImageView btn_next,btn_previous;
    private CardView card_view;
    private DrawingView drawingView;
    private char[] caps={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private char[] smalls={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private String[] nums={"Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Tweleve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty",
    "Twenty one","Twenty two","Twenty three","Twenty four","Twenty five","Twenty six","Twenty seven","Twenty eight","Twenty nine","Thirty","Thirty one","Thirty two","Thirty three","Thirty four","Thirty five","Thirty six","Thirty seven","Thirty eight","Thirty nine","Forty",
    "Forty one","Forty two","Forty three","Forty four","Forty five","Forty six","Forty seven","Forty eight","Forty nine","Fifty","Fifty one","Fifty two","Fifty three","Fifty four","Fifty five",
    "Fifty six","Fifty seven","Fifty eight","Fifty nine","Sixty","Sixty one","Sixty two","Sixty three","Sixty four","Sixty five","Sixty six","Sixty seven","Sixty eight","Sixty nine","Seventy",
    "Seventy one","Seventy two","Seventy three","Seventy four","Seventy five","Seventy six","Seventy seven","Seventy eight","Seventy nine","Eighty","Eighty one","Eighty two","Eighty three","Eighty four","Eighty five","Eighty six","Eighty seven","Eighty eight","Eighty nine","Ninety",
    "Ninety one","Ninety two","Ninety three","Ninety four","Ninety five","Ninety six","Ninety seven","Ninety eight","Ninety nine","Hundread"};
    private int index=0;
    private int numbers=0;
    private CheckBox ch_capslock;
    private String subject;
    private TextToSpeech textToSpeech;

    private ConnectionDetector cd;
    private boolean isInternetPresent = false;
    private MediaPlayer mp;
    private int clicks=0;
    private AdView adviewlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets);

        init();
    }

    private void init()
    {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        adviewlayout = (AdView) findViewById(R.id.banner);
        //isInternetPresent=false;

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.setPitch(0.6f);
                    textToSpeech.setSpeechRate(2);
                }
            }
        });

        subject=getIntent().getStringExtra(Utils.SUBJECT);
        tv_originaltxt=(TextView)findViewById(R.id.tv_originaltxt);
        btn_clear=(RelativeLayout)findViewById(R.id.btn_clear);
        btn_next=(ImageView)findViewById(R.id.btn_next);
        btn_previous=(ImageView)findViewById(R.id.btn_previous);
        ch_capslock=(CheckBox)findViewById(R.id.ch_capslock);
        card_view=(CardView)findViewById(R.id.card_view);
        drawingView=new DrawingView(AlphabetsActivity.this);
        card_view.addView(drawingView);

        if(subject.equalsIgnoreCase(Utils.ALPHABETS))
        {
            ch_capslock.setVisibility(View.VISIBLE);
            displayLetters(index);
        }
        else
        {
            ch_capslock.setVisibility(View.GONE);
            displayNumbers(numbers);
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawingView.clear();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(subject.equalsIgnoreCase(Utils.ALPHABETS))
                {
                    if(index==25)
                        index=0;
                    else
                        index=index+1;
                    displayLetters(index);
                }
                else
                {
                   if(numbers==100)
                       numbers=0;
                   else
                       numbers=numbers+1;
                   displayNumbers(numbers);
                }
                clicks++;
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(subject.equalsIgnoreCase(Utils.ALPHABETS))
                {
                    if(index>0)
                        index=index-1;
                    displayLetters(index);
                }
                else
                {
                   if(numbers>0)
                       numbers=numbers-1;
                   displayNumbers(numbers);
                }

                clicks++;
            }
        });

        ch_capslock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                displayLetters(index);
            }
        });
    }

    private void displayLetters(int index)
    {
        String text="";
        if(ch_capslock.isChecked())
            text=text+caps[index];
        else
            text=text+smalls[index];

        tv_originaltxt.setText(""+text);
        drawingView.clear();
        TextToSpeechFunction(text);
    }

    private void displayNumbers(int num)
    {
        tv_originaltxt.setText(""+num);
        drawingView.clear();
        TextToSpeechFunction(nums[num]);
    }

    public void TextToSpeechFunction(String text)
    {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onBackPressed() {
        stopSpeech();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSpeech();
    }

    private void stopSpeech()
    {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
