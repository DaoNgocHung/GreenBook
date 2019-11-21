package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Fragments.HomeFragment;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.Transformations.TossTransformation;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private Button btnLoginLogin;
    private ImageButton imgbtnLoginGoogle;
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
    private String userEmail = "";
    private FirebaseFirestore db;
    private boolean checkEC = false;
    MyCallback myCallback;
    private String hoTenFB="";
    private Timestamp ngayThangNSFB;
    private String urlHinhDaiDienFB = "";

    UsersModel usersModel = new UsersModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        addControls();
        SharedPreferences sharedPreferences= this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        edtLoginUsername.setText(sharedPreferences.getString("emailUser",null));
        edtLoginPass.setText(sharedPreferences.getString("passUser",null));
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
                    openLoadingDialog();
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
        userEmail = edtLoginUsername.getText().toString();
        //authenticate user
        auth.signInWithEmailAndPassword(userEmail, edtLoginPass.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingDialog.cancel();
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        } else {
                            if(auth.getCurrentUser().isEmailVerified() == true){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email", userEmail.trim());
                                savePref();
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

    private void getFbInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.d(TAG, "fb json object: " + object);
                            Log.d(TAG, "fb graph response: " + response);
                            if (object.has("email")) {
                                userEmail = object.getString("email");
                                usersModel.setEmail(object.getString("email"));
                                urlHinhDaiDienFB = "http://graph.facebook.com/" + object.getString("id") + "/picture?type=large";
                                hoTenFB=object.getString("first_name") + object.getString("last_name");

                                ngayThangNSFB = doiNgay(object.getString("birthday"));
                                readData(new MyCallback() {
                                    @Override
                                    public void onCallBack(Boolean checkEC2) {
                                        if (checkEC2 == true){
                                            usersModel.setHoTen(hoTenFB);
                                            usersModel.setHinhDaiDien(urlHinhDaiDienFB);
                                            usersModel.setNgayThangNS(ngayThangNSFB);
                                            usersModel.setGioiTinh(true);
                                            usersModel.setSoDT("");
                                            usersModel.setSoSachDaMua(0);
                                            usersModel.setTien(0.0);
                                            db.collection("UserModel").document().set(usersModel);
                                        }
                                    }
                                }, userEmail);

                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("email", usersModel.getEmail().trim());
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,first_name,last_name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            getFbInfo();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //Chuyen doi ngay thang nam sinh tu Facebook
    public Timestamp doiNgay(String d){
        Timestamp timeStampDate = null;
        try{
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = (Date) formatter.parse(d);
            timeStampDate = new Timestamp(date);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return timeStampDate;
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
        imgbtnLoginGoogle = findViewById(R.id.imgbtnLoginGoogle);
        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPass = findViewById(R.id.edtLoginPass);
        chkLoginRemember = findViewById(R.id.chkLoginRemember);
        txtLoginForgot = findViewById(R.id.txtLoginForgot);
        txtLoginSignUp = findViewById(R.id.txtLoginSignUp);
        auth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.buttonFacebookLogin);
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile", "user_birthday");
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        db = FirebaseFirestore.getInstance();
    }
    public void checkUserExist(String email){
        db.collection("UserCollection")
                .whereEqualTo("email", email).limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().isEmpty()){
                            checkEC = true;
                            myCallback.onCallBack(checkEC);
                        }
                        else{
                            checkEC = false;
                            myCallback.onCallBack(checkEC);
                        }
                    }
                });
    }
    private void openLoadingDialog() {
        loadingDialog = new Dialog(LoginActivity.this, R.style.CustomDialog);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }
    public interface MyCallback{
        void onCallBack(Boolean checkEC);

    }
    public  boolean readData(MyCallback myCallback, String userEmail){
        this.myCallback = myCallback;
        checkUserExist(userEmail);
        return checkEC;

    }
    //Lưu thông tin người dùng vào sharedPreference
    public void savePref(){
        SharedPreferences sharedPreferences= this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("emailUser", this.edtLoginUsername.getText().toString());
        editor.putString("passUser", this.edtLoginPass.getText().toString());
        editor.apply();
    }
}
