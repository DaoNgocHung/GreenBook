package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.InfoBookViewPagerAdapter;
import com.anhhung.greenbook.Fragments.CommentBookFragment;
import com.anhhung.greenbook.Fragments.InfoBookFragment;
import com.anhhung.greenbook.Fragments.SummaryBookFragment;
import com.anhhung.greenbook.Models.BillDetailModel;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.CollectionMapModel;
import com.anhhung.greenbook.Models.DanhGiaModel;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.api.Distribution;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import jp.wasabeef.blurry.Blurry;

import static java.lang.Math.round;

public class InfoBookActivity extends AppCompatActivity {

    private TabLayout tabLayoutInfoBook;
    private AppBarLayout appBarLayoutInfoBook;
    private ViewPager viewPagerInfoBook;
    private TextView txtInfoNameBook, txtInfoBookDownload, txtInfoBookPrice, txtInfoVote, txtInfoRateButton;
    private ImageView imgInFoBookCover;
    private ImageButton imgbtnInfoBookFavor;
    private Toolbar actionToolbarInfoBook;
    private RatingBar rateInfoBook;
    private Button btnInfoBookBuy;
    private Dialog dialogBuy;
    BooksModel booksModel = new BooksModel();
    UsersModel usersModel = new UsersModel();
    private String TAG = "InfoBookActivity - Error";
    private UploadTask uploadTask;
    RatingBar rtBarDialog;
    private AlertDialog dialog;
    private long userRateNums;
    private float starRateNums;
    private UserRateCount userRateCount;
    private StarRateTotal starRateTotal;
    private float starAverage;
    private ConstraintLayout constraintLayout;
    private LinearLayout linearLayout;
    private int linearHeight = 0 ;
    private int linearWidth = 0;
    private RewardedAd rewardedAd;


    private String emailUser;
    SharedPreferences sharedPreferences;

    private boolean isFavor = false;  //Biến Test cho Favor

    FirebaseFirestore db;
    private Calendar c = Calendar.getInstance();
    private String year = c.get(Calendar.YEAR)+"";
    private String month = c.get(Calendar.MONTH)+1+"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_book);
        addControls();
        addEvents();
    }

    private void addEvents() {
        //RatingBar
        txtInfoRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlertDialog();
            }
        });

        //ViewPager
        InfoBookViewPagerAdapter adapter = new InfoBookViewPagerAdapter(getSupportFragmentManager());
        //Add Fragment
        SummaryBookFragment summaryBookFragment = newInstance(booksModel.getGioiThieuSach());
        InfoBookFragment infoBookFragment = newInstance(booksModel.getNXB(), booksModel.getTacGia(),
                booksModel.getDanhMuc(), booksModel.getNgonNgu(), booksModel.getIdDM());
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
                        if(booksModel.getGiaTien() > 0){
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
                                                                                        //Cập nhật thống kê
                                                                                        db.collection("CollectionMap").document((year)).collection(month)
                                                                                                .limit(1)
                                                                                                .whereEqualTo("collectionMapName", booksModel.getTenSach())
                                                                                                .get()
                                                                                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                                                    @Override
                                                                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                                                                        if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                                                                                                            Calendar ca = Calendar.getInstance();
                                                                                                            CollectionMapModel collectionMapModel = new CollectionMapModel(1, booksModel.getGiaTien(), booksModel.getTenSach(), ca.MONTH);
                                                                                                            db.collection("CollectionMap").document((year)).collection(month).document(booksModel.getTenSach())
                                                                                                                    .set(collectionMapModel);
                                                                                                        } else {
                                                                                                            db.collection("CollectionMap").document((year)).collection(month)
                                                                                                                    .limit(1)
                                                                                                                    .whereEqualTo("collectionMapName", booksModel.getTenSach())
                                                                                                                    .get()
                                                                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                                                                                                                    CollectionMapModel collectionMapModel = documentSnapshot.toObject(CollectionMapModel.class);
                                                                                                                                    double tien = collectionMapModel.getTongDoanhThuTien() + booksModel.getGiaTien();
                                                                                                                                    db.collection("CollectionMap").document((year + "")).collection(month + "").document(booksModel.getTenSach())
                                                                                                                                            .update("tongDoanhThuTien", tien, "tongSachBan", collectionMapModel.getTongSachBan()+1);
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                        }
                                                                                                    }
                                                                                                });

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
                        }
                        else {
                            RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                                @Override
                                public void onRewardedAdLoaded() {
                                    if (rewardedAd.isLoaded()) {
                                        Activity activityContext = getParent();
                                        RewardedAdCallback adCallback = new RewardedAdCallback() {
                                            public void onRewardedAdOpened() {
                                                // Ad opened.
                                            }

                                            public void onRewardedAdClosed() {
                                                // Ad closed.
                                            }

                                            @Override
                                            public void onUserEarnedReward(@NonNull com.google.android.gms.ads.rewarded.RewardItem rewardItem) {

                                            }

                                            public void onRewardedAdFailedToShow(int errorCode) {
                                                // Ad failed to display
                                            }
                                        };
                                        rewardedAd.show(activityContext, adCallback);
                                    } else {
                                        Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                                    }
                                }

                                @Override
                                public void onRewardedAdFailedToLoad(int errorCode) {
                                    // Ad failed to load.
                                }
                            };
                            rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

                        }
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
        linearLayout = findViewById(R.id.linearLayoutInfoBook);
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
        rateInfoBook = findViewById(R.id.rateInfoBook);
        txtInfoRateButton = findViewById(R.id.txtRate);
        btnInfoBookBuy = findViewById(R.id.btnInfoBookBuy);
        rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");
        Glide.with(InfoBookActivity.this)
                .load(booksModel.getBiaSach())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(final Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        linearLayout.post(new Runnable(){
                            public void run(){
                                linearHeight = linearLayout.getHeight();
                                linearWidth = linearLayout.getWidth();
                                if (linearHeight != 0 && linearWidth != 0){
                                    linearLayout.setBackground(scaleImage(resource, linearWidth, linearHeight));
                                    Blurry.with(InfoBookActivity.this)
                                            .radius(10)
                                            .sampling(8)
                                            .color(Color.argb(60, 240, 240, 20))
                                            .async()
                                            .animate(500)
                                            .onto(linearLayout);

                                }
                            }
                        });
                        return false;
                    }
                })
                .into(imgInFoBookCover);


        constraintLayout = findViewById(R.id.constraintLayoutInfoBook);
        txtInfoNameBook.setText(booksModel.getTenSach());
        txtInfoBookDownload.setText("Downloaded: " + booksModel.getSoNguoiMua());
        txtInfoBookPrice.setText("Price: " + booksModel.getGiaTien());
        txtInfoVote.setText("(" + booksModel.getLuotDanhGia() + ")");


        db = FirebaseFirestore.getInstance();
        sharedPreferences = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        emailUser = sharedPreferences.getString("emailUser", null);
        computeStarRatingBar(rateInfoBook);
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
    private interface UserRateCount{
        void onCallBack(long userRateNums);

    }
    private  interface StarRateTotal{
        void onCallBack(float starRateNums);
    }
    public void readUserRateCount(UserRateCount userRateCount) {
        this.userRateCount = userRateCount;
        getNumsUserDanhGia();
    }
    public void readStarTotal(StarRateTotal starRateTotal) {
        this.starRateTotal = starRateTotal;
        getNumsStarDanhGia();
    }
    public static SummaryBookFragment newInstance(String gioiThieu) {
        SummaryBookFragment f = new SummaryBookFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("gioiThieu", gioiThieu);
        f.setArguments(args);
        return f;
    }

    public static InfoBookFragment newInstance(String NXB, String tacGia, String danhMuc, String ngonNgu, String idDM) {
        InfoBookFragment f = new InfoBookFragment();
        Bundle args = new Bundle();
        args.putString("NXB", NXB);
        args.putString("tacGia", tacGia);
        args.putString("danhMuc", danhMuc);
        args.putString("ngonNgu", ngonNgu);
        args.putString("idDM",idDM);
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
    public void displayAlertDialog() {
        Log.d("INFO","Show Dialog");
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.activity_rating_dialog, null);
        final TextView txtRtBar = alertLayout.findViewById(R.id.txtRatingBarStar);
        rtBarDialog =  alertLayout.findViewById(R.id.ratingBarDialog);
        final Button btnCanelDialog = alertLayout.findViewById(R.id.btnCancelDialog);
        Button btnRateDialog = alertLayout.findViewById(R.id.btnDanhGiaDialog);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        rtBarDialog.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtRtBar.setText("You rated " + v +" stars.");
            }
        });
        btnRateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateStar();
                computeStarRatingBar(rateInfoBook);


            }
        });
        btnCanelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    private void rateStar(){
        db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection")
                .document(booksModel.getTenSach()).collection("DanhGiaCollection")
                .whereEqualTo("userName", emailUser)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(final QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().isEmpty()){
                            Log.d("INFO", "Create Star");
                            DanhGiaModel dg = new DanhGiaModel();
                            dg.setRateStar(rtBarDialog.getRating());
                            dg.setUserName(sharedPreferences.getString("emailUser",null));
                            db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection").document(booksModel.getTenSach())
                                    .collection("DanhGiaCollection").document(emailUser).set(dg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    addStarRateBook((float) 0.0);
                                    dialog.cancel();
                                }
                            });
                        }
                        else{
                            Log.d("INFO", "Update Start");
                            db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection")
                                    .document(booksModel.getTenSach()).collection("DanhGiaCollection").document(emailUser)
                                    .update("userName", emailUser, "rateStar", rtBarDialog.getRating())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    DanhGiaModel dgia = new DanhGiaModel();
                                    dgia = queryDocumentSnapshots.getDocuments().get(0).toObject(DanhGiaModel.class);
                                    addStarRateBook(dgia.getRateStar());
                                    dialog.cancel();
//                                    db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection").document(booksModel.getTenSach())
//                                            .collection("DanhGiaCollection").document(emailUser).get()
//                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    DanhGiaModel dgia = new DanhGiaModel();
//                                                    dgia = task.getResult().toObject(DanhGiaModel.class);
//                                                    addStarRateBook(dgia.getRateStar());
//                                                    dialog.cancel();
//
//                                                }
//                                            });
                                }
                            });

                        }

                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        dialog.cancel();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    private void getNumsUserDanhGia(){
        db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection")
                .document(booksModel.getTenSach()).collection("DanhGiaCollection").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        userRateNums = queryDocumentSnapshots.size();
                        userRateCount.onCallBack(userRateNums);
                    }
                });

    }
    private void getNumsStarDanhGia(){
        db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection")
                .document(booksModel.getTenSach()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        BooksModel booksModel = new BooksModel();
                        booksModel = documentSnapshot.toObject(BooksModel.class);
                        starRateTotal.onCallBack(booksModel.getDanhGia());
                    }
                });

    }
    private void computeStarRatingBar(final RatingBar rtBar){
        readStarTotal(new StarRateTotal() {
            @Override
            public void onCallBack(final float starRateNums) {
                readUserRateCount(new UserRateCount() {
                    @Override
                    public void onCallBack(long userRateNums) {
                        txtInfoVote.setText("("+userRateNums+")");
                        if(userRateNums!=0){
                            starAverage = round((starRateNums/userRateNums)*10)/10;
                        }
                        else{
                            starAverage = 0;
                        }
                        rtBar.setRating(starAverage);
                    }
                });
            }
        });
    }
    private void addStarRateBook(final Float numOldUpdate){
        readStarTotal(new StarRateTotal() {
            @Override
            public void onCallBack(float starRateNums) {
                updateStarRateBook(rtBarDialog, numOldUpdate, starRateNums);
            }
        });
    }
    private void updateStarRateBook(RatingBar rtBar, Float numOldUpdate, Float starRateNums){
        db.collection("DanhMucCollection").document(booksModel.getIdDM()).collection("SachColection")
                .document(booksModel.getTenSach()).update("danhGia", starRateNums+rtBar.getRating()-numOldUpdate);

    }
    private Drawable scaleImage (Drawable image, float scaleWidth, float scaleHeight) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable)image).getBitmap();

        int sizeX = Math.round(scaleWidth);
        int sizeY = Math.round(scaleHeight);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);
        image = new BitmapDrawable(getResources(), bitmapResized);
        return image;

    }
}

