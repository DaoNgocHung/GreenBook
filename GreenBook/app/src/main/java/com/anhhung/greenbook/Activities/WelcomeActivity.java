package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anhhung.greenbook.R;


public class WelcomeActivity extends AppCompatActivity {

    private Button btnWelcomeSignIn;
    private TextView txtWelcomeSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtWelcomeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnWelcomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        txtWelcomeSignUp = findViewById(R.id.txtWelcomeSignUp);
        btnWelcomeSignIn = findViewById(R.id.btnWelcomeSignIn);
    }
}
