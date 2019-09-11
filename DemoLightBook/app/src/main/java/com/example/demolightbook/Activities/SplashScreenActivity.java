package com.example.demolightbook.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demolightbook.Activities.IntroActivity;
import com.example.demolightbook.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imgLogo;
    TextView txtLabelWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        addControls();
    }

    private void addControls() {
        imgLogo = findViewById(R.id.imgLogo);
        txtLabelWelcome = findViewById(R.id.txtLabelWelcome);
        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.mytransition);

        imgLogo.setAnimation(myAnimation);
        txtLabelWelcome.setAnimation(myAnimation);

        final Intent intent = new Intent(this, IntroActivity.class);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}