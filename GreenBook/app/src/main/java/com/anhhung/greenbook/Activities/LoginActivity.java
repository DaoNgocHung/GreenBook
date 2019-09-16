package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLoginLogin;
    private ImageButton imgbtnLoginFacebook, imgbtnLoginTwitter, imgbtnLoginGoogle;
    private EditText edtLoginUsername, edtLoginPass;
    private CheckBox chkLoginRemember;
    private TextView txtLoginForgot, txtLoginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgbtnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Login with Google", Toast.LENGTH_LONG).show();
            }
        });

        imgbtnLoginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Login with Twitter", Toast.LENGTH_LONG).show();
            }
        });

        imgbtnLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Login with Facebook", Toast.LENGTH_LONG).show();
            }
        });

        txtLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtLoginUsername.getText() == null || edtLoginPass.getText() == null
                        || (edtLoginPass.getText() == null &&  edtLoginUsername.getText() == null)){
                    Toast.makeText(LoginActivity.this,"Please enter full information",Toast.LENGTH_LONG).show();
                }
                else {
                    // code kiểm tra đăng nhập
                }
            }
        });
    }

    private void addControls() {
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        imgbtnLoginFacebook = findViewById(R.id.imgbtnLoginFaceBook);
        imgbtnLoginGoogle = findViewById(R.id.imgbtnLoginGoogle);
        imgbtnLoginTwitter = findViewById(R.id.imgbtnLoginTwitter);
        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPass = findViewById(R.id.edtLoginPass);
        chkLoginRemember = findViewById(R.id.chkLoginRemember);
        txtLoginForgot = findViewById(R.id.txtLoginForgot);
        txtLoginSignUp = findViewById(R.id.txtLoginSignUp);
    }
}
