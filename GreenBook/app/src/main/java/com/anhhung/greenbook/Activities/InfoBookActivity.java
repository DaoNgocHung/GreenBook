package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.InfoBookViewPagerAdapter;
import com.anhhung.greenbook.Fragments.CommentBookFragment;
import com.anhhung.greenbook.Fragments.InfoBookFragment;
import com.anhhung.greenbook.Fragments.SummaryBookFragment;
import com.anhhung.greenbook.Models.BillDetailModel;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

import io.opencensus.stats.Aggregation;

public class InfoBookActivity extends AppCompatActivity {

    private TabLayout tabLayoutInfoBook;
    private AppBarLayout appBarLayoutInfoBook;
    private ViewPager viewPagerInfoBook;
    private TextView txtInfoNameBook, txtInfoBookDownload, txtInfoBookPrice, txtInfoVote;
    private ImageView imgInFoBookCover;
    private ImageButton imgbtnInfoBookFavor;
    private Toolbar actionToolbarInfoBook;
    private RatingBar ratingBar;
    private Button btnInfoBookBuy;
    private Dialog dialogBuy;
    BooksModel booksModel = new BooksModel();
    UsersModel usersModel = new UsersModel();
    private String TAG = "InfoBookActivity - Error";

    private String emailUser;
    SharedPreferences sharedPreferences;

    private boolean isFavor = false;  //Biến Test cho Favor

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_book);
        addControls();
        addEvents();
    }

    private void addEvents() {
        //ViewPager
        InfoBookViewPagerAdapter adapter = new InfoBookViewPagerAdapter(getSupportFragmentManager());
        //Add Fragment
        SummaryBookFragment summaryBookFragment = newInstance(booksModel.getGioiThieuSach());
        InfoBookFragment infoBookFragment = newInstance(booksModel.getNXB(), booksModel.getTacGia(), booksModel.getDanhMuc(), booksModel.getNgonNgu());
        CommentBookFragment commentBookFragment = newInstance(booksModel.getTenSach(), booksModel.getDanhMuc(), booksModel.getIdDM());
        adapter.AddFragment(infoBookFragment, "Info");
        adapter.AddFragment(summaryBookFragment, "Summary");
        adapter.AddFragment(commentBookFragment, "Comment");
        //adapter Setup
        viewPagerInfoBook.setAdapter(adapter);
        tabLayoutInfoBook.setupWithViewPager(viewPagerInfoBook);
        //Favorite Book
        imgbtnInfoBookFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavor == false) {
                    imgbtnInfoBookFavor.setImageResource(R.drawable.ic_favor);
                    isFavor = true;
                    Toast.makeText(InfoBookActivity.this, "You have added favorites", Toast.LENGTH_SHORT).show();
                } else {
                    imgbtnInfoBookFavor.setImageResource(R.drawable.ic_not_favor);
                    isFavor = false;
                    Toast.makeText(InfoBookActivity.this, "You have canceled your favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Button Back on toolbar
        actionToolbarInfoBook.setTitle("");
        setSupportActionBar(actionToolbarInfoBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Button Mua sách
        btnInfoBookBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuy = new Dialog(InfoBookActivity.this);
                dialogBuy.setContentView(R.layout.dialog_buy_book);
                dialogBuy.setCancelable(false);
                dialogBuy.show();
                Button btnYesBuy, btnNoBuy;
                btnYesBuy = dialogBuy.findViewById(R.id.btnYesBuy);
                btnNoBuy = dialogBuy.findViewById(R.id.btnNoBuy);
                // Nhấn chọn mua
                btnYesBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        emailUser = sharedPreferences.getString("emailUser", null);

                        // Kiểm tra tiền của khách hàng so với giá tiền cuốn sách
                        db.collection("UserModel")
                                .whereEqualTo("email", emailUser)
                                .limit(1)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                usersModel = document.toObject(UsersModel.class);
                                                if (booksModel.getGiaTien() >= usersModel.getTien()) {
                                                    Toast.makeText(InfoBookActivity.this, "Tài khoản của bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    //Thêm sách vào thư viện của user
                                                    db.collection("UserModel").document(emailUser)
                                                            .collection("LibraryCollection").document(booksModel.getTenSach())
                                                            .set(booksModel)
                                                            //Thêm sách vào thư viện thành công
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    double tien = usersModel.getTien() - booksModel.getGiaTien();
                                                                    long soSachDaMua = usersModel.getSoSachDaMua() + 1;

                                                                    //Trừ tiền của người dùng
                                                                    db.collection("UserModel").document(emailUser)
                                                                            .update("tien", tien,
                                                                                    "soSachDaMua", soSachDaMua)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {

                                                                                    //Tạo hoá đơn
                                                                                    BillDetailModel billDetailModel =
                                                                                            new BillDetailModel(emailUser, Timestamp.now(), booksModel.getTenSach(), booksModel.getGiaTien(), "Transaction Successful");
                                                                                    db.collection("UserModel").document(emailUser)
                                                                                            .collection("BillCollection").document()
                                                                                            .set(billDetailModel);

                                                                                    //Update thuộc tính soNguoiMua của sách
                                                                                    db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection")
                                                                                            .whereEqualTo("tenSach",booksModel.getTenSach())
                                                                                            .limit(1)
                                                                                            .get()
                                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    if(task.isSuccessful()){
                                                                                                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                                                                                            // here you can get the id.
                                                                                                            String id = documentSnapshot.getId();
                                                                                                            db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection").document(id)
                                                                                                                    .update("soNguoiMua", booksModel.getSoNguoiMua()+1);
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                    Toast.makeText(InfoBookActivity.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {

                                                                                    //Tạo hoá đơn
                                                                                    BillDetailModel billDetailModel =
                                                                                            new BillDetailModel(emailUser, Timestamp.now(), booksModel.getTenSach(), booksModel.getGiaTien(), "Transaction Error");
                                                                                    db.collection("UserModel").document(emailUser)
                                                                                            .collection("BillCollection").document()
                                                                                            .set(billDetailModel);
                                                                                }
                                                                            });
                                                                }
                                                            })

                                                            // Sách không thêm vào thư viện được
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    BillDetailModel billDetailModel =
                                                                            new BillDetailModel(emailUser, Timestamp.now(), booksModel.getTenSach(), booksModel.getGiaTien(), "Book not added to library");
                                                                    db.collection("UserModel").document(emailUser)
                                                                            .collection("BillCollection").document()
                                                                            .set(billDetailModel);
                                                                }
                                                            });
                                                }
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                        // Thêm sách vào thư viện của người dùng, trừ tiền, thêm vào bill


                        dialogBuy.dismiss();
                    }
                });

                btnNoBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(InfoBookActivity.this,booksModel.getIdDM(),Toast.LENGTH_SHORT).show();
                        dialogBuy.dismiss();
                    }
                });
            }
        });
    }

    private void addControls() {
        loadBundleData();
        tabLayoutInfoBook = findViewById(R.id.tabLayoutInfoBook);
        appBarLayoutInfoBook = findViewById(R.id.appBarLayoutInfoBook);
        viewPagerInfoBook = findViewById(R.id.viewPagerInfoBook);
        txtInfoNameBook = findViewById(R.id.txtInfoNameBook);
        txtInfoBookDownload = findViewById(R.id.txtInfoBookDownloaded);
        txtInfoBookPrice = findViewById(R.id.txtInfoBookPrice);
        imgInFoBookCover = findViewById(R.id.imgInFoBookCover);
        imgbtnInfoBookFavor = findViewById(R.id.imgbtnInfoBookFavor);
        actionToolbarInfoBook = findViewById(R.id.actionToolbarInfoBook);
        txtInfoVote = findViewById(R.id.txtInfoVote);
        ratingBar = findViewById(R.id.rateInfoBook);
        btnInfoBookBuy = findViewById(R.id.btnInfoBookBuy);
        Glide.with(InfoBookActivity.this)
                .load(booksModel.getBiaSach())
                .into(imgInFoBookCover);
        txtInfoNameBook.setText(booksModel.getTenSach());
        txtInfoBookDownload.setText("Downloaded: " + booksModel.getSoNguoiMua());
        txtInfoBookPrice.setText("Price: " + booksModel.getGiaTien());
        txtInfoVote.setText("(" + booksModel.getLuotDanhGia() + ")");
        ratingBar.setRating(booksModel.getDanhGia());

        db = FirebaseFirestore.getInstance();
        sharedPreferences = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
    }

    private void loadBundleData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            booksModel.setBiaSach(bundle.getString("anhBia", ""));
            booksModel.setTenSach(bundle.getString("tenSach", ""));
            booksModel.setSoNguoiMua(bundle.getLong("soNguoiMua", 0));
            booksModel.setDanhGia(bundle.getFloat("danhGia", 0));
            booksModel.setNoiDung(bundle.getString("noiDung", " "));
            booksModel.setGioiThieuSach(bundle.getString("gioiThieu", " "));
            booksModel.setGiaTien(bundle.getDouble("giaTien", 0));
            booksModel.setDanhMuc(bundle.getString("danhMuc", ""));
            //booksModel.setNgayUpload(bundle.get("ngayUpload",""));
            booksModel.setTacGia(bundle.getString("tacGia", ""));
            booksModel.setNXB(bundle.getString("NXB", ""));
            booksModel.setNgonNgu(bundle.getString("ngonNgu", ""));
            booksModel.setSoNguoiMua(bundle.getLong("soNguoiMua", 0));
            booksModel.setLuotDanhGia(bundle.getLong("luotDanhGia", 0));
            booksModel.setIdDM(bundle.getString("idDM",""));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static SummaryBookFragment newInstance(String gioiThieu) {
        SummaryBookFragment f = new SummaryBookFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("gioiThieu", gioiThieu);
        f.setArguments(args);
        return f;
    }

    public static InfoBookFragment newInstance(String NXB, String tacGia, String danhMuc, String ngonNgu) {
        InfoBookFragment f = new InfoBookFragment();
        Bundle args = new Bundle();
        args.putString("NXB", NXB);
        args.putString("tacGia", tacGia);
        args.putString("danhMuc", danhMuc);
        args.putString("ngonNgu", ngonNgu);
        f.setArguments(args);
        return f;
    }

    public static CommentBookFragment newInstance(String tenSach, String danhMuc, String idDM){
        CommentBookFragment f = new CommentBookFragment();
        Bundle args = new Bundle();
        args.putString("tenSach", tenSach);
        args.putString("danhMuc", danhMuc);
        args.putString("idDM", idDM);
        f.setArguments(args);
        return f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnuShare) {
            Toast.makeText(InfoBookActivity.this, "SHARE", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
