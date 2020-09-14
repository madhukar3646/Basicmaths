package com.app.childmaths;

import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.Random;

public class LessthanGreaterThanTest extends AppCompatActivity {

    private String level;
    private int qts_count = 0, marks_count = 0;
    private Random random;
    private int a, b,result,opt1=1, opt2=0;
    private TextView tv_marks, tv_question, tv_opt1, tv_opt2;
    private ImageView btn_next, iv_outputimage, iv_sound;
    private RelativeLayout layout_opt1, layout_opt2, layout_correctans;
    private TextView tv_correctans;
    private int screenWidth, screenHeight;
    private String yes="YES",no="NO";

    private ConnectionDetector cd;
    private boolean isInternetPresent = false;
    private MediaPlayer mp;
    private AdView adviewlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessthan_greater_than_test);
        init();
    }

    private void init() {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        adviewlayout = (AdView) findViewById(R.id.banner);
        //isInternetPresent=false;

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        random = new Random();
        level = getIntent().getStringExtra(Utils.LEVEL);

        layout_opt1 = (RelativeLayout) findViewById(R.id.layout_opt1);
        layout_opt2 = (RelativeLayout) findViewById(R.id.layout_opt2);
        layout_correctans = (RelativeLayout) findViewById(R.id.layout_correctans);
        layout_correctans.setVisibility(View.GONE);
        tv_correctans = (TextView) findViewById(R.id.tv_correctans);

        tv_marks = (TextView) findViewById(R.id.tv_marks);
        tv_marks.setText("" + marks_count + "/" + qts_count);
        tv_question = (TextView) findViewById(R.id.tv_question);
        tv_opt1 = (TextView) findViewById(R.id.tv_opt1);
        tv_opt2 = (TextView) findViewById(R.id.tv_opt2);
        iv_outputimage = (ImageView) findViewById(R.id.iv_outputimage);
        iv_outputimage.getLayoutParams().width = screenWidth / 4;
        iv_outputimage.getLayoutParams().height = screenWidth / 4;
        iv_outputimage.setVisibility(View.GONE);
        btn_next = (ImageView) findViewById(R.id.btn_next);
        btn_next.getLayoutParams().width = screenWidth / 4;
        btn_next.getLayoutParams().height = screenWidth / 4;
        btn_next.setVisibility(View.GONE);

        iv_sound = (ImageView) findViewById(R.id.iv_sound);
        iv_sound.getLayoutParams().width = screenWidth / 7;
        iv_sound.getLayoutParams().height = screenWidth / 7;

        updateNextQuestion();


        iv_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isSound) {
                    Utils.isSound = false;
                    iv_sound.setImageResource(R.drawable.soundoff);
                    stopMusic();
                } else {
                    Utils.isSound = true;
                    iv_sound.setImageResource(R.drawable.soundicon);
                }

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_next.setVisibility(View.GONE);
                layout_correctans.setVisibility(View.GONE);
                iv_outputimage.setVisibility(View.GONE);
                setOptionsClickable(true);
                updateNextQuestion();
            }
        });

        layout_opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOptionsClickable(false);
                if (result == opt1) {
                    marks_count++;
                    layout_opt1.setBackgroundResource(R.drawable.round_rect_green);
                    iv_outputimage.setImageResource(R.drawable.righticon);
                    if (Utils.isSound)
                        playCorrectMusic();
                } else {
                    layout_opt1.setBackgroundResource(R.drawable.round_rect_red);
                    iv_outputimage.setImageResource(R.drawable.wrongicon);
                    if (Utils.isSound)
                        playWrongMusic();
                }

                tv_marks.setText("Score : " + marks_count + "/" + qts_count);
                iv_outputimage.setVisibility(View.VISIBLE);
                displayOutputImageAnimation();
            }
        });
        layout_opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOptionsClickable(false);
                if (result == opt2) {
                    marks_count++;
                    layout_opt2.setBackgroundResource(R.drawable.round_rect_green);
                    iv_outputimage.setImageResource(R.drawable.righticon);
                    if (Utils.isSound)
                        playCorrectMusic();
                } else {
                    layout_opt2.setBackgroundResource(R.drawable.round_rect_red);
                    iv_outputimage.setImageResource(R.drawable.wrongicon);
                    if (Utils.isSound)
                        playWrongMusic();
                }

                tv_marks.setText("Score : " + marks_count + "/" + qts_count);
                iv_outputimage.setVisibility(View.VISIBLE);
                displayOutputImageAnimation();
            }
        });
    }


    private void setOptionsClickable(boolean isClickable) {
        if (isClickable) {
            layout_opt1.setBackgroundResource(R.drawable.round_rect_skyblue);
            layout_opt2.setBackgroundResource(R.drawable.round_rect_skyblue);
        }
        layout_opt1.setClickable(isClickable);
        layout_opt2.setClickable(isClickable);
    }

    private void updateNextQuestion()
    {
            qts_count++;
            addition(level);

       /* if (qts_count % 10 == 0) {
            if (isInternetPresent)
                displayFullscreenAd();
        }*/
    }


    private void addition(String level)
    {
        if (level.equalsIgnoreCase(Utils.LEVEL1)) {
            a = random.nextInt(10);
            b = random.nextInt(10);
        } else if (level.equalsIgnoreCase(Utils.LEVEL2)) {
            a = 10 + random.nextInt(90);
            b = 10 + random.nextInt(90);
        } else if (level.equalsIgnoreCase(Utils.LEVEL3)) {
            a = 100 + random.nextInt(900);
            b = 100 + random.nextInt(900);
        } else if (level.equalsIgnoreCase(Utils.LEVEL4)) {
            a = 1000 + random.nextInt(90000);
            b = 1000 + random.nextInt(90000);
        }

        int symbol=random.nextInt(4);
        String question="";
        if(symbol==0)
        {
            question= a + " < " + b ;
            if(a<b)
                result = 1;
            else
                result=0;
        }
        else if(symbol==1)
        {
            question= a + " > " + b ;
            if(a>b)
                result = 1;
            else
                result=0;
        }
        else if(symbol==2)
        {
            question= a + " ≥ " + b ;
            if(a>=b)
                result = 1;
            else
                result=0;
        }
        else if(symbol==3)
        {
            question= a + " ≤ " + b ;
            if(a<=b)
                result = 1;
            else
                result=0;
        }

        if(result==0)
          tv_correctans.setText("Answer is : " + no);
        else
            tv_correctans.setText("Answer is : " + yes);

            questionPrinting(question);
    }

    private void questionPrinting(String question)
    {
        tv_question.setText("Q): " + question);
    }

    public void displayOutputImageAnimation() {
        ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);     // animation duration in milliseconds
        animation.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
        animation.setInterpolator(new OvershootInterpolator());
        iv_outputimage.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                layout_correctans.setVisibility(View.VISIBLE);
                displayAnswerAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void displayAnswerAnimation() {
        ScaleAnimation animation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        animation.setDuration(500);     // animation duration in milliseconds
        animation.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
        animation.setInterpolator(new OvershootInterpolator());
        layout_correctans.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btn_next.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void playCorrectMusic() {
        mp=null;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();
    }

    private void playWrongMusic() {
        mp=null;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();
    }

    private void stopMusic() {
        if (mp != null)
        {
            if(mp.isPlaying())
                mp.stop();
            mp.reset();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopMusic();
    }
}