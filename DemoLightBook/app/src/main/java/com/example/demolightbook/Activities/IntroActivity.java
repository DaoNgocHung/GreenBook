package com.example.demolightbook.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.demolightbook.Adapters.IntroViewPagerAdapter;
import com.example.demolightbook.R;
import com.example.demolightbook.Models.IntroItem;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tab_indicator;
    private Button btnNext, btnGetStarted;
    private Animation btnAnim;

    IntroViewPagerAdapter introViewPagerAdapter;
    List<IntroItem> mList = new ArrayList<>();

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //when this activity is about to be lauch we need to check if its opened before or not
        if(restorePrefData()){
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);
            finish();
        }

        setContentView(R.layout.activity_intro);
        addDataIntro();
        addControls();
        addEvents();
    }

    private void addDataIntro() {
        //fill list screen
        mList.add(new IntroItem("Yello","Yellow is the color between orange and green on the spectrum of visible light. It is evoked by light with a dominant wavelength of roughly 570–590 nm.",R.drawable.yellowcircle));
        mList.add(new IntroItem("Green","Green is the color between blue and yellow on the visible spectrum. It is evoked by light which has a dominant wavelength of roughly 495–570 nm.",R.drawable.greencircle));
        mList.add(new IntroItem("Orange","Orange is the colour between yellow and red on the spectrum of visible light. Human eyes perceive orange when observing light with a dominant wavelength between roughly 585 and 620 nanometres.",R.drawable.orangecircle));
    }

    private void addControls() {

        viewPager = findViewById(R.id.viewPager);
        tab_indicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.btnNext);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        //setup viewpager
        introViewPagerAdapter  = new IntroViewPagerAdapter(this,mList);
        viewPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tab_indicator.setupWithViewPager(viewPager);
    }

    private void addEvents() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if(position == mList.size() - 1){ //when we reach to the last screen
                    //TODO: show the GETSTARTED Button and hide the indicator and the next button
                    loadLastScreen();
                }
            }
        });

        // tablayout add change listener
        tab_indicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this,LoginActivity.class);
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
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tab_indicator.setVisibility(View.VISIBLE);
        //TODO: ADD an animation the getstarted button
        //setup animation button
        btnGetStarted.setAnimation(btnAnim);
    }
}
