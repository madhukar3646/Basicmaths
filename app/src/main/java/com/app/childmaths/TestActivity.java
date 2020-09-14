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

public class TestActivity extends AppCompatActivity {

    private String subject,level;
    private int qts_count=0,marks_count=0;
    private Random random;
    private int a,b,result,opt1,opt2,opt3,opt4;
    private TextView tv_marks,tv_question,tv_opt1,tv_opt2,tv_opt3,tv_opt4;
    private ImageView btn_next,iv_outputimage,iv_sound;
    private RelativeLayout layout_opt1,layout_opt2,layout_opt3,layout_opt4,layout_correctans;
    private TextView tv_correctans;
    private int screenWidth,screenHeight;

    private ConnectionDetector cd;
    private boolean isInternetPresent = false;
    private MediaPlayer mp;
    private AdView adviewlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        init();
    }

    private void init()
    {
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        adviewlayout = (AdView) findViewById(R.id.banner);
        //isInternetPresent=false;
        DisplayMetrics metrics=this.getResources().getDisplayMetrics();
        screenWidth=metrics.widthPixels;
        screenHeight=metrics.heightPixels;

      random=new Random();
      subject=getIntent().getStringExtra(Utils.SUBJECT);
      level=getIntent().getStringExtra(Utils.LEVEL);

      layout_opt1=(RelativeLayout)findViewById(R.id.layout_opt1);
      layout_opt2=(RelativeLayout)findViewById(R.id.layout_opt2);
      layout_opt3=(RelativeLayout)findViewById(R.id.layout_opt3);
      layout_opt4=(RelativeLayout)findViewById(R.id.layout_opt4);
      layout_correctans=(RelativeLayout)findViewById(R.id.layout_correctans);
      layout_correctans.setVisibility(View.GONE);
      tv_correctans=(TextView)findViewById(R.id.tv_correctans);

        tv_marks=(TextView)findViewById(R.id.tv_marks);
        tv_marks.setText(""+marks_count+"/"+qts_count);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_opt1=(TextView)findViewById(R.id.tv_opt1);
        tv_opt2=(TextView)findViewById(R.id.tv_opt2);
        tv_opt3=(TextView)findViewById(R.id.tv_opt3);
        tv_opt4=(TextView)findViewById(R.id.tv_opt4);
        iv_outputimage=(ImageView)findViewById(R.id.iv_outputimage);
        iv_outputimage.getLayoutParams().width=screenWidth/4;
        iv_outputimage.getLayoutParams().height=screenWidth/4;
        iv_outputimage.setVisibility(View.GONE);
        btn_next=(ImageView)findViewById(R.id.btn_next);
        btn_next.getLayoutParams().width=screenWidth/4;
        btn_next.getLayoutParams().height=screenWidth/4;
        btn_next.setVisibility(View.GONE);

        iv_sound=(ImageView)findViewById(R.id.iv_sound);
        iv_sound.getLayoutParams().width=screenWidth/7;
        iv_sound.getLayoutParams().height=screenWidth/7;

        updateNextQuestion();


        iv_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utils.isSound)
                {
                    Utils.isSound=false;
                    iv_sound.setImageResource(R.drawable.soundoff);
                    stopMusic();
                }
                else
                {
                    Utils.isSound=true;
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
            public void onClick(View view)
            {
                setOptionsClickable(false);
                    if(result==opt1)
                    {
                        marks_count++;
                        layout_opt1.setBackgroundResource(R.drawable.round_rect_green);
                        iv_outputimage.setImageResource(R.drawable.righticon);
                        if(Utils.isSound)
                           playCorrectMusic();
                    }
                    else
                    {
                        layout_opt1.setBackgroundResource(R.drawable.round_rect_red);
                        iv_outputimage.setImageResource(R.drawable.wrongicon);
                        if(Utils.isSound)
                           playWrongMusic();
                    }

                tv_marks.setText("Score : "+marks_count+"/"+qts_count);
                iv_outputimage.setVisibility(View.VISIBLE);
                displayOutputImageAnimation();
            }
        });
        layout_opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setOptionsClickable(false);
                    if(result==opt2)
                    {
                        marks_count++;
                        layout_opt2.setBackgroundResource(R.drawable.round_rect_green);
                        iv_outputimage.setImageResource(R.drawable.righticon);
                        if(Utils.isSound)
                            playCorrectMusic();
                    }
                    else
                    {
                        layout_opt2.setBackgroundResource(R.drawable.round_rect_red);
                        iv_outputimage.setImageResource(R.drawable.wrongicon);
                        if(Utils.isSound)
                            playWrongMusic();
                    }

                tv_marks.setText("Score : "+marks_count+"/"+qts_count);
                iv_outputimage.setVisibility(View.VISIBLE);
                displayOutputImageAnimation();
            }
        });
        layout_opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setOptionsClickable(false);
                    if(result==opt3)
                    {
                        marks_count++;
                        layout_opt3.setBackgroundResource(R.drawable.round_rect_green);
                        iv_outputimage.setImageResource(R.drawable.righticon);
                        if(Utils.isSound)
                            playCorrectMusic();
                    }
                    else
                    {
                        layout_opt3.setBackgroundResource(R.drawable.round_rect_red);
                        iv_outputimage.setImageResource(R.drawable.wrongicon);
                        if(Utils.isSound)
                            playWrongMusic();
                    }

                tv_marks.setText("Score : "+marks_count+"/"+qts_count);
                iv_outputimage.setVisibility(View.VISIBLE);
                displayOutputImageAnimation();
            }
        });
        layout_opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setOptionsClickable(false);
                    if(result==opt4)
                    {
                        marks_count++;
                        layout_opt4.setBackgroundResource(R.drawable.round_rect_green);
                        iv_outputimage.setImageResource(R.drawable.righticon);
                        if(Utils.isSound)
                            playCorrectMusic();
                    }
                    else
                    {
                        layout_opt4.setBackgroundResource(R.drawable.round_rect_red);
                        iv_outputimage.setImageResource(R.drawable.wrongicon);
                        if(Utils.isSound)
                            playWrongMusic();
                    }

                tv_marks.setText("Score : "+marks_count+"/"+qts_count);
                iv_outputimage.setVisibility(View.VISIBLE);
                displayOutputImageAnimation();
            }
        });

    }


    private void setOptionsClickable(boolean isClickable)
    {
        if(isClickable)
        {
            layout_opt1.setBackgroundResource(R.drawable.round_rect_skyblue);
            layout_opt2.setBackgroundResource(R.drawable.round_rect_skyblue);
            layout_opt3.setBackgroundResource(R.drawable.round_rect_skyblue);
            layout_opt4.setBackgroundResource(R.drawable.round_rect_skyblue);
        }
        layout_opt1.setClickable(isClickable);
        layout_opt2.setClickable(isClickable);
        layout_opt3.setClickable(isClickable);
        layout_opt4.setClickable(isClickable);
    }

    private void updateNextQuestion()
    {
        qts_count++;
        if(subject.equalsIgnoreCase(Utils.ADDITION))
            addition(level);
        else if(subject.equalsIgnoreCase(Utils.SUBSTRACTION))
            substractions(level);
        else if(subject.equalsIgnoreCase(Utils.MULTIPLICATION))
            multiplications(level);
        else if(subject.equalsIgnoreCase(Utils.DIVISION))
            divisions(level);

       /* if(qts_count%10==0)
        {
            if(isInternetPresent)
                displayFullscreenAd();
        }*/
    }


    private void addition(String level)
    {
        if(level.equalsIgnoreCase(Utils.LEVEL1))
        {
            a=random.nextInt(10);
            b=random.nextInt(10);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL2))
        {
            a=10+random.nextInt(90);
            b=10+random.nextInt(90);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL3))
        {
            a=100+random.nextInt(900);
            b=100+random.nextInt(900);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL4))
        {
            a=1000+random.nextInt(90000);
            b=1000+random.nextInt(90000);
        }

        result=a+b;
        tv_correctans.setText("Answer is : "+result);
        int order1[]=new int[]{result+1,result-1,result+2,result};
        int order2[]=new int[]{result+1,result-1,result,result+2};
        int order3[]=new int[]{result+1,result,result+2,result-1};
        int order4[]=new int[]{result,result-1,result+2,result+1};
        int order=random.nextInt(4);

        if(order==0)
            questionAndOptionsPrinting(order1,a+" + "+b+" = ?");
        else if(order==1)
            questionAndOptionsPrinting(order2,a+" + "+b+" = ?");
        else if(order==2)
            questionAndOptionsPrinting(order3,a+" + "+b+" = ?");
        else if(order==3)
            questionAndOptionsPrinting(order4,a+" + "+b+" = ?");

    }

    private void substractions(String level)
    {
        if(level.equalsIgnoreCase(Utils.LEVEL1))
        {
            a=random.nextInt(10);
            b=random.nextInt(10);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL2))
        {
            a=10+random.nextInt(90);
            b=10+random.nextInt(90);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL3))
        {
            a=100+random.nextInt(900);
            b=100+random.nextInt(900);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL4))
        {
            a=1000+random.nextInt(90000);
            b=1000+random.nextInt(90000);
        }

        result=a-b;
        tv_correctans.setText("Answer is : "+result);
        int order1[]=new int[]{result+1,result-1,result+2,result};
        int order2[]=new int[]{result+1,result-1,result,result+2};
        int order3[]=new int[]{result+1,result,result+2,result-1};
        int order4[]=new int[]{result,result-1,result+2,result+1};
        int order=random.nextInt(4);

        if(order==0)
            questionAndOptionsPrinting(order1,a+" - "+b+" = ?");
        else if(order==1)
            questionAndOptionsPrinting(order2,a+" - "+b+" = ?");
        else if(order==2)
            questionAndOptionsPrinting(order3,a+" - "+b+" = ?");
        else if(order==3)
            questionAndOptionsPrinting(order4,a+" - "+b+" = ?");

    }

    private void multiplications(String level)
    {
        if(level.equalsIgnoreCase(Utils.LEVEL1))
        {
            a=random.nextInt(10);
            b=random.nextInt(10);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL2))
        {
            a=10+random.nextInt(90);
            b=random.nextInt(10);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL3))
        {
            a=100+random.nextInt(900);
            b=10+random.nextInt(90);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL4))
        {
            a=1000+random.nextInt(90000);
            b=100+random.nextInt(900);
        }

        result=a*b;
        tv_correctans.setText("Answer is : "+result);
        int order1[]=new int[]{result+1,result-1,result+2,result};
        int order2[]=new int[]{result+1,result-1,result,result+2};
        int order3[]=new int[]{result+1,result,result+2,result-1};
        int order4[]=new int[]{result,result-1,result+2,result+1};
        int order=random.nextInt(4);

        if(order==0)
            questionAndOptionsPrinting(order1,a+" x "+b+" = ?");
        else if(order==1)
            questionAndOptionsPrinting(order2,a+" x "+b+" = ?");
        else if(order==2)
            questionAndOptionsPrinting(order3,a+" x "+b+" = ?");
        else if(order==3)
            questionAndOptionsPrinting(order4,a+" x "+b+" = ?");

    }

    private boolean isPrimeOrNot(int number)
    {
        for(int i=2;i<(number/2)+1;i++)
        {
            if((number%i)==0)
            {
                return false;
            }
        }
        return true;
    }

    private void divisions(String level)
    {
        if(level.equalsIgnoreCase(Utils.LEVEL1))
        {
            a=1+random.nextInt(10);
            while (isPrimeOrNot(a))
                a=1+random.nextInt(10);

            b=1+random.nextInt(10);
            if(a==1)
                b=1;
            int rem=a%b;
            while (rem!=0)
            {
                b=1+random.nextInt(10);
                if(b==1)
                    continue;
                rem=a%b;
            }
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL2))
        {
            a=10+random.nextInt(90);
            while (isPrimeOrNot(a))
                a=10+random.nextInt(90);

            b=1+random.nextInt(10);
            int rem=a%b;
            while (rem!=0)
            {
                b=1+random.nextInt(10);
                if(b==1)
                    continue;
                rem=a%b;
            }
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL3))
        {
            a=100+random.nextInt(900);
            while (isPrimeOrNot(a))
                a=100+random.nextInt(900);
            b=10+random.nextInt(90);
        }
        else if(level.equalsIgnoreCase(Utils.LEVEL4))
        {
            a=1000+random.nextInt(90000);
            while (isPrimeOrNot(a))
                a=1000+random.nextInt(90000);

            b=100+random.nextInt(900);
        }

        result=a/b;
        int rem=a%b;
        if(rem!=0)
            tv_correctans.setText("Answer is : "+result+" Remainder is "+rem);
        else
            tv_correctans.setText("Answer is : "+result);
        int order1[]=new int[]{result+1,result-1,result+2,result};
        int order2[]=new int[]{result+1,result-1,result,result+2};
        int order3[]=new int[]{result+1,result,result+2,result-1};
        int order4[]=new int[]{result,result-1,result+2,result+1};
        int order=random.nextInt(4);

        if(order==0)
            questionAndOptionsPrinting(order1,a+" / "+b+" = ?");
        else if(order==1)
            questionAndOptionsPrinting(order2,a+" / "+b+" = ?");
        else if(order==2)
            questionAndOptionsPrinting(order3,a+" / "+b+" = ?");
        else if(order==3)
            questionAndOptionsPrinting(order4,a+" / "+b+" = ?");

    }

    private void questionAndOptionsPrinting(int optns[],String question)
    {
       opt1=optns[0];
       opt2=optns[1];
       opt3=optns[2];
       opt4=optns[3];

       tv_question.setText("Q): "+question);
       tv_opt1.setText(""+opt1);
       tv_opt2.setText(""+opt2);
       tv_opt3.setText(""+opt3);
       tv_opt4.setText(""+opt4);
    }

    public void displayOutputImageAnimation()
    {
        ScaleAnimation animation =  new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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

    public void displayAnswerAnimation()
    {
        ScaleAnimation animation =  new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
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

    private void playCorrectMusic()
    {
        mp=null;
        mp= MediaPlayer.create(getApplicationContext(),R.raw.correct);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();
    }

    private void playWrongMusic()
    {
        mp=null;
        mp=MediaPlayer.create(getApplicationContext(),R.raw.wrong);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();
    }

    private void stopMusic()
    {
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
