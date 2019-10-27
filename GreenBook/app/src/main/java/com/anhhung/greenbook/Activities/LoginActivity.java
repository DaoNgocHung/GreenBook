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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
    CallbackManager mCallbackManager;
    private String TAG = "LOG: ";
    LoginButton loginButton;
    GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;


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
                signInGoogle();
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
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        auth = FirebaseAuth.getInstance();
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
                                openLoadingDialog();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", edtLoginUsername.getText().toString().trim());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        //updateUI(currentUser);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
    //Sign in with google
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
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
        loginButton = findViewById(R.id.buttonFacebookLogin);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void openLoadingDialog() {
        loadingDialog = new Dialog(LoginActivity.this, R.style.CustomDialog);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

}
