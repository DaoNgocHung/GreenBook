package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private CircleImageView imgInfoUserAvatar;
    private TextView txtInfoUserEmail, txtInfoUserPhone, txtInfoUserBirthDay, txtInfoUserChangeDate;
    private ImageButton imgbtnInfoUserEdit;
    private EditText edtInfoUserName;
    private RadioButton rdInfoUserMale, rdInfoUserFemale;
    private SharedPreferences sharedPreferences;

    private boolean isCheckEdit = false;
    private String emailUser;
    private UsersModel usersModel;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);
        addControls();
        loadData();
        addEvents();
    }

    private void addEvents() {
        imgbtnInfoUserEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCheckEdit == false){
                    imgbtnInfoUserEdit.setImageResource(R.drawable.ic_complete_edit_user);
                    edtInfoUserName.setEnabled(true);
                    rdInfoUserFemale.setEnabled(true);
                    rdInfoUserMale.setEnabled(true);
                    txtInfoUserChangeDate.setVisibility(view.VISIBLE);
                    isCheckEdit = true;
                }
                else{
                    imgbtnInfoUserEdit.setImageResource(R.drawable.ic_edit_user);
                    edtInfoUserName.setEnabled(false);
                    rdInfoUserFemale.setEnabled(false);
                    rdInfoUserMale.setEnabled(false);
                    txtInfoUserChangeDate.setVisibility(view.INVISIBLE);
                    isCheckEdit = false;
                    editDataProfile();
                    loadData();
                }
            }
        });

        txtInfoUserChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void editDataProfile(){
        db.collection("UserModel").document(emailUser)
                .update("hoTen",edtInfoUserName.getText().toString(),
                        "ngayThangNS",doiNgay(txtInfoUserBirthDay.getText().toString()));
        if(rdInfoUserMale.isChecked()){
            db.collection("UserModel").document(emailUser)
                    .update("gioiTinh",true);
        }
        else{
            db.collection("UserModel").document(emailUser)
                    .update("gioiTinh",false);
        }
    }

    private void loadData() {
        emailUser = sharedPreferences.getString("emailUser",null);
        db.collection("UserModel").document(emailUser)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        usersModel  = documentSnapshot.toObject(UsersModel.class);
                        txtInfoUserEmail.setText(usersModel.getEmail());
                        txtInfoUserPhone.setText(usersModel.getSoDT());
                        edtInfoUserName.setText(usersModel.getHoTen());
                        if (usersModel.getGioiTinh()){
                            rdInfoUserMale.setChecked(true);
                        } else {
                            rdInfoUserFemale.setChecked(true);
                        }
                        txtInfoUserBirthDay.setText(DateFormat.format("dd/MM/yyyy", usersModel.getNgayThangNS().toDate()).toString());
                        Glide.with(InfoUserActivity.this)
                                .load(usersModel.getHinhDaiDien())
                                .into(imgInfoUserAvatar);
                    }
                });

    }

    private void addControls() {
        imgInfoUserAvatar = findViewById(R.id.imgInfoUserAvatar);
        txtInfoUserEmail = findViewById(R.id.txtInfoUserEmail);
        txtInfoUserPhone = findViewById(R.id.txtInfoUserPhone);
        txtInfoUserBirthDay = findViewById(R.id.txtInfoUserBirthDay);
        txtInfoUserChangeDate = findViewById(R.id.txtInfoUserChangeDate);
        imgbtnInfoUserEdit = findViewById(R.id.imgbtnInfoUserEdit);
        edtInfoUserName = findViewById(R.id.edtInfoUserName);
        rdInfoUserMale = findViewById(R.id.rdInfoUserMale);
        rdInfoUserFemale = findViewById(R.id.rdInfoUserFemale);
        sharedPreferences= this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();
    }

    //Chuyen doi ngay thang nam sinh tu Facebook
    public Timestamp doiNgay(String d){
        Timestamp timeStampDate = null;
        try{
            java.text.DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            timeStampDate = new Timestamp(date);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return timeStampDate;
    }

    // Hiển thị DatePicker
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month += 1;
        String str_date = dayOfMonth +"/" + month + "/" + year;
        txtInfoUserBirthDay.setText(str_date);
    }
}
