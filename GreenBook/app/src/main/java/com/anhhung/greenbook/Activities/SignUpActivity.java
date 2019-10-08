package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtSignUpUsername, edtSignUpPass, edtSignUpConfirmPass, edtSignUpEmail;
    private Button btnSignUpSignUp;
    private ImageButton imgbtnSignUpFaceBook, imgbtnSignUpTwitter, imgbtnSignUpGoogle;
    private TextView txtSignUpSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    Dialog loadingDialog;
    private boolean errorSignUp = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSignUpSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_Registration_Information() == true){
                    openLoadingDialog();
                    sendEmailVerify();

                }
                else{
                    errorSignUp = false;
                }
            }
        });
        //Mở màn hình Đăng nhập
        txtSignUpSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnSignUpFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this,"Login with Facebook", Toast.LENGTH_LONG).show();
            }
        });

        imgbtnSignUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this,"Login with Google",Toast.LENGTH_LONG).show();
            }
        });

        imgbtnSignUpTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this,"Login with Twitter",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void sendEmailVerify(){
        auth.createUserWithEmailAndPassword(edtSignUpEmail.getText().toString(), edtSignUpPass.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingDialog.cancel();
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            auth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignUpActivity.this, "Registered successfully. Please check your email for verification",
                                                        Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                        }

                    }
                });
    }
    private void openLoadingDialog() {
        loadingDialog = new Dialog(SignUpActivity.this, R.style.CustomDialog);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    private boolean check_Registration_Information() {
        // Kiểm tra:
        // Nếu thông tin nhập vào có username không bị trùng và confirm giống hoàn toàn pass và thông tin không có để trống
        //Thông tin đế trống
        if(edtSignUpUsername.getText().toString().equals("")){
            Toast.makeText(SignUpActivity.this,"Enter user name!",Toast.LENGTH_LONG).show();
            edtSignUpUsername.requestFocus();
            errorSignUp = true;
        }
        else if(edtSignUpPass.getText().toString().equals("")){
            Toast.makeText(SignUpActivity.this,"Enter password!",Toast.LENGTH_LONG).show();
            edtSignUpPass.requestFocus();
            errorSignUp = true;
        }
        else if(edtSignUpConfirmPass.getText().toString().equals("")){
            Toast.makeText(SignUpActivity.this,"Enter confirm password!",Toast.LENGTH_LONG).show();
            edtSignUpConfirmPass.requestFocus();
            errorSignUp = true;
        }
        else if(edtSignUpEmail.getText().toString().equals("") || edtSignUpPass.getText().toString().equals("")){
            Toast.makeText(SignUpActivity.this,"Enter mail address!",Toast.LENGTH_LONG).show();
            edtSignUpEmail.requestFocus();
            errorSignUp = true;
        }
        else if (edtSignUpPass.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            edtSignUpPass.setText("");
            edtSignUpConfirmPass.setText("");
            edtSignUpPass.requestFocus();
            errorSignUp = true;
        }
        //Nếu Password không giống với Confirm Password
        else if(!edtSignUpPass.getText().toString().equals(edtSignUpConfirmPass.getText().toString())){
            Toast.makeText(SignUpActivity.this,"Password is diferent from password confirm!",Toast.LENGTH_LONG).show();
            edtSignUpPass.setText("");
            edtSignUpConfirmPass.setText("");
            edtSignUpPass.requestFocus();
            errorSignUp = true;
        }
        if(errorSignUp != false){
            return false;
        }
        return true;
    }

    private void addControls() {
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPass = findViewById(R.id.edtSignUpPass);
        edtSignUpConfirmPass = findViewById(R.id.edtSignUpConfirmPass);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        btnSignUpSignUp = findViewById(R.id.btnSignUpSignUp);
        imgbtnSignUpFaceBook = findViewById(R.id.imgbtnSignUpFaceBook);
        imgbtnSignUpGoogle = findViewById(R.id.imgbtnSignUpGoogle);
        imgbtnSignUpTwitter = findViewById(R.id.imgbtnSignUpTwitter);
        txtSignUpSignIn = findViewById(R.id.txtSignUpSignIn);
        auth = FirebaseAuth.getInstance();
    }
}
