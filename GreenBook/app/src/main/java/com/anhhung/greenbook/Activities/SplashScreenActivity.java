package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anhhung.greenbook.R;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imgSplashLogo, imgSplashTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        addControls();
    }

    private void addControls() {
        final SharedPreferences sharedPreferences = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        imgSplashLogo = findViewById(R.id.imgSplashLogo);
        imgSplashTitle = findViewById(R.id.imgSplashTitle);
        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);

        imgSplashLogo.setAnimation(myAnimation);
        imgSplashTitle.setAnimation(myAnimation);

        final Intent intent = new Intent(this, IntroActivity.class);
        final Intent intent2 = new Intent(this, MainActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    if(sharedPreferences.getString("emailUser",null)!= null && sharedPreferences.getString("emailUser",null)!=""){
                        startActivity(intent2);
                    }
                    else{
                        startActivity(intent);
                    }
                    finish();
                }
            }
        };
        timer.start();
    }
}
