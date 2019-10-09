package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_book);
        addControls();
        addEvents();
    }

    private void addEvents() {
        InfoBookViewPagerAdapter adapter = new InfoBookViewPagerAdapter(getSupportFragmentManager());
        //Add Fragment
        adapter.AddFragment(new InfoBookFragment(),"Info");
        adapter.AddFragment(new SummaryBookFragment(), "Summary");
        adapter.AddFragment(new CommentBookFragment(), "Comment");
        //adapter Setup
        viewPagerInfoBook.setAdapter(adapter);
        tabLayoutInfoBook.setupWithViewPager(viewPagerInfoBook);
    }

    private void addControls() {
        tabLayoutInfoBook = findViewById(R.id.tabLayoutInfoBook);
        appBarLayoutInfoBook = findViewById(R.id.appBarLayoutInfoBook);
        viewPagerInfoBook = findViewById(R.id.viewPagerInfoBook);
        txtInfoNameBook = findViewById(R.id.txtInfoNameBook);
        imgInFoBookCover = findViewById(R.id.imgInFoBookCover);
    }
}
