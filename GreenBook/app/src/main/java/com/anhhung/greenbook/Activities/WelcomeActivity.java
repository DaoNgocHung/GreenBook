package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anhhung.greenbook.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends AppCompatActivity {

    private Button btnWelcomeSignIn;
    private TextView txtWelcomeSignUp;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if(firebaseUser != null){
//            Intent intentMain = new Intent(WelcomeActivity.this,MainActivity.class);
//            startActivity(intentMain);
//            finish();
//        }
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtWelcomeSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnWelcomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        txtWelcomeSignUp = findViewById(R.id.txtWelcomeSignUp);
        btnWelcomeSignIn = findViewById(R.id.btnWelcomeSignIn);
    }
}
