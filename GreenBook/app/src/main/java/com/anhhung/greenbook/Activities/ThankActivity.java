package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anhhung.greenbook.R;

public class ThankActivity extends AppCompatActivity {

    private Button btnThankLetGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThankLetGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThankActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnThankLetGo = findViewById(R.id.btnThankLetGo);
    }
}
