package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.Transformations.TossTransformation;

public class LoginActivity extends AppCompatActivity {

    private Button btnLoginLogin;
    private ImageButton imgbtnLoginFacebook, imgbtnLoginTwitter, imgbtnLoginGoogle;
    private EditText edtLoginUsername, edtLoginPass;
    private CheckBox chkLoginRemember;
    private TextView txtLoginForgot, txtLoginSignUp;
    private FirebaseAuth auth;
    Dialog loadingDialog;
    private boolean errorLogin = false;

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
                finish();
            }
        });

        txtLoginForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_Registration_Information() == true){
                    loginEmailPassword();
                }
                else {
                    errorLogin = false;
                }

            }
        });
    }

    private boolean check_Registration_Information() {
        if(edtLoginUsername.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"Enter user name!",Toast.LENGTH_LONG).show();
            edtLoginUsername.requestFocus();
            errorLogin = true;
        }
        else if(edtLoginPass.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"Enter password!",Toast.LENGTH_LONG).show();
            edtLoginPass.requestFocus();
            errorLogin = true;
        }
        if(errorLogin == false){
            return true;
        }
        else return false;
    }
    private void loginEmailPassword() {
        //authenticate user
        auth.signInWithEmailAndPassword(edtLoginUsername.getText().toString(), edtLoginPass.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //loadingDialog.cancel();
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        } else {
                            if(auth.getCurrentUser().isEmailVerified() == true){
                                Intent intent = new Intent(LoginActivity.this, ThankActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "You are new user. Please check your email for verification before login!", Toast.LENGTH_SHORT).show();
                            }

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
        auth = FirebaseAuth.getInstance();
    }
}
