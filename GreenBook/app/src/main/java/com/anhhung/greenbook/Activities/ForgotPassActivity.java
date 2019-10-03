package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anhhung.greenbook.R;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText edtForgotEmail;
    private Button btnForgotResetPass;
    private TextView txtForgotBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtForgotBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        edtForgotEmail = findViewById(R.id.edtForgotEmail);
        btnForgotResetPass = findViewById(R.id.btnForgotResetPass);
        txtForgotBack = findViewById(R.id.txtForgotBack);
    }
}
