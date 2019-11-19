package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anhhung.greenbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {

    private EditText edtPassOldChange, edtPassNewChange, edtPassConfirmChange;
    private Button btnChangePass;
    private Toolbar actionToolbarChangePass;
    private SharedPreferences sharedPreferences;
    private String emailUser = "";
    private String TAG = "Change Password";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtPassOldChange = findViewById(R.id.edtPassOldChange);
        edtPassNewChange = findViewById(R.id.edtPassNewChange);
        edtPassConfirmChange = findViewById(R.id.edtPassConfirmChange);
        btnChangePass = findViewById(R.id.btnChangePass);
        actionToolbarChangePass = findViewById(R.id.actionToolbarChangePass);
        actionToolbarChangePass.setTitle("Change Password");
        sharedPreferences = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        emailUser = sharedPreferences.getString("emailUser", null);
    }

    private void addEvents() {
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkConfirm(edtPassNewChange.getText().toString().trim(),edtPassConfirmChange.getText().toString().trim())
                    && countString(edtPassOldChange.getText().toString()) == true
                    && countString(edtPassNewChange.getText().toString()) == true
                    && countString(edtPassConfirmChange.getText().toString())== true){
                    AuthCredential credential = EmailAuthProvider.getCredential(emailUser,edtPassOldChange.getText().toString().trim());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(edtPassNewChange.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "Password updated");
                                                    Toast.makeText(ChangePassActivity.this,"Change Password Success",Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    Log.d(TAG, "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "Error auth failed");
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(ChangePassActivity.this,"Please check again your information",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public boolean checkConfirm(String pass1, String pass2){
        if(pass1.equals(pass2))
            return true;
        else
            return false;
    }

    public boolean countString(String s){
        int count = 0;
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++){
            count++;
        }
        if(count < 6)
            return false;
        return true;
    }
}
