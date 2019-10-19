package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.InfoBookViewPagerAdapter;
import com.anhhung.greenbook.Fragments.CommentBookFragment;
import com.anhhung.greenbook.Fragments.InfoBookFragment;
import com.anhhung.greenbook.Fragments.SummaryBookFragment;
import com.anhhung.greenbook.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class InfoBookActivity extends AppCompatActivity {

    private TabLayout tabLayoutInfoBook;
    private AppBarLayout appBarLayoutInfoBook;
    private ViewPager viewPagerInfoBook;
    private TextView txtInfoNameBook;
    private ImageView imgInFoBookCover;
    private ImageButton imgbtnInfoBookFavor;
    private Toolbar actionToolbarInfoBook;

    private boolean isFavor = false;  //Biến Test cho Favor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_book);
        addControls();
        addEvents();
    }

    private void addEvents() {
        //ViewPager
        InfoBookViewPagerAdapter adapter = new InfoBookViewPagerAdapter(getSupportFragmentManager());
        //Add Fragment
        adapter.AddFragment(new InfoBookFragment(),"Info");
        adapter.AddFragment(new SummaryBookFragment(), "Summary");
        adapter.AddFragment(new CommentBookFragment(), "Comment");
        //adapter Setup
        viewPagerInfoBook.setAdapter(adapter);
        tabLayoutInfoBook.setupWithViewPager(viewPagerInfoBook);

        //Favorite Book
        imgbtnInfoBookFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavor == false){
                    imgbtnInfoBookFavor.setImageResource(R.drawable.ic_favor);
                    isFavor = true;
                    Toast.makeText(InfoBookActivity.this,"You have added favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    imgbtnInfoBookFavor.setImageResource(R.drawable.ic_not_favor);
                    isFavor = false;
                    Toast.makeText(InfoBookActivity.this,"You have canceled your favorites",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button Back on toolbar
        actionToolbarInfoBook.setTitle("");
        setSupportActionBar(actionToolbarInfoBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addControls() {
        tabLayoutInfoBook = findViewById(R.id.tabLayoutInfoBook);
        appBarLayoutInfoBook = findViewById(R.id.appBarLayoutInfoBook);
        viewPagerInfoBook = findViewById(R.id.viewPagerInfoBook);
        txtInfoNameBook = findViewById(R.id.txtInfoNameBook);
        imgInFoBookCover = findViewById(R.id.imgInFoBookCover);
        imgbtnInfoBookFavor = findViewById(R.id.imgbtnInfoBookFavor);
        actionToolbarInfoBook = findViewById(R.id.actionToolbarInfoBook);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
