package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.anhhung.greenbook.Adapters.IntroViewPagerAdapter;
import com.anhhung.greenbook.Models.IntroItemModel;
import com.anhhung.greenbook.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPagerIntro;
    private TabLayout tab_indicator_Intro;
    private Button btnIntroNext, btnIntroGetStarted;
    private Animation btnAnim;

    IntroViewPagerAdapter introViewPagerAdapter;
    List<IntroItemModel> mList = new ArrayList<>();

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //when this activity is about to be lauch we need to check if its opened before or not
        if(restorePrefData()){
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_intro);
        addDataIntro();
        addControls();
        addEvents();
    }

    private void addDataIntro() {
        //fill list screen
        mList.add(new IntroItemModel("Easy to sign in","Login with Facebook or Google",R.drawable.pic_intro_fb_google));
        mList.add(new IntroItemModel("Diverse books","We have many types of books",R.drawable.pic_intro_book));
        mList.add(new IntroItemModel("Thanks","Wish you have the best experience",R.drawable.pic_intro_satisfied));
    }

    private void addControls() {

        viewPagerIntro = findViewById(R.id.viewPagerIntro);
        tab_indicator_Intro = findViewById(R.id.tab_indicator_Intro);
        btnIntroNext = findViewById(R.id.btnIntroNext);
        btnIntroGetStarted = findViewById(R.id.btnIntroGetStarted);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        //setup viewpager
        introViewPagerAdapter  = new IntroViewPagerAdapter(this,mList);
        viewPagerIntro.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tab_indicator_Intro.setupWithViewPager(viewPagerIntro);
    }

    private void addEvents() {
        btnIntroNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPagerIntro.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    viewPagerIntro.setCurrentItem(position);
                }
                if(position == mList.size() - 1){ //when we reach to the last screen
                    //TODO: show the GETSTARTED Button and hide the indicator and the next button
                    loadLastScreen();
                }
            }
        });

        // tablayout add change listener
        tab_indicator_Intro.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size() - 1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnIntroGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this,WelcomeActivity.class);
                startActivity(intent);

                //also we need to save a boolean value to storage so next time when the user run app
                //we could know that user is already checked the intro screen activity
                //i'm going to use shared preferences to that process
                savePrefsData();
                finish();
            }
        });
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplication().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplication().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    //show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen() {
        btnIntroNext.setVisibility(View.INVISIBLE);
        btnIntroGetStarted.setVisibility(View.VISIBLE);
        tab_indicator_Intro.setVisibility(View.VISIBLE);
        //TODO: ADD an animation the getstarted button
        //setup animation button
        btnIntroGetStarted.setAnimation(btnAnim);
    }

}
