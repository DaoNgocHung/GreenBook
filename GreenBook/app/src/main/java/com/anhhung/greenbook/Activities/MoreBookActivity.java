package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.anhhung.greenbook.R;

public class MoreBookActivity extends AppCompatActivity {

    private Toolbar actionToolbarMoreBook;
    private Intent intent;
    private String danhMuc;
    private RecyclerView rViewMoreBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_book);
        addControl();
        addEvent();
    }

    private void addEvent() {
        //Button Back on toolbar
        actionToolbarMoreBook.setTitle(danhMuc);
        setSupportActionBar(actionToolbarMoreBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void addControl() {
        actionToolbarMoreBook = findViewById(R.id.actionToolbarMoreBook);
        intent = getIntent();
        danhMuc = intent.getStringExtra("danhMuc");
        rViewMoreBook = findViewById(R.id.rViewMoreBook);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
