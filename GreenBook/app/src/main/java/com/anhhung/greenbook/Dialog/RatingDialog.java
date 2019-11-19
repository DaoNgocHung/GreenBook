package com.anhhung.greenbook.Dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anhhung.greenbook.R;

public class RatingDialog extends AppCompatActivity {
    private TextView txtRatingBarStar;
    private RatingBar rtBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_dialog);
        addControls();
        addEvent();
    }

    private void addEvent() {

    }

    private void addControls() {
        rtBar = findViewById(R.id.ratingBarDialog);
        txtRatingBarStar = findViewById(R.id.txtRatingBarStar);
    }
}
