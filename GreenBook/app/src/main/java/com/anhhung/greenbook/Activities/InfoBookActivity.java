package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.InfoBookViewPagerAdapter;
import com.anhhung.greenbook.Fragments.CommentBookFragment;
import com.anhhung.greenbook.Fragments.InfoBookFragment;
import com.anhhung.greenbook.Fragments.SummaryBookFragment;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.sql.Timestamp;

import io.opencensus.stats.Aggregation;

public class InfoBookActivity extends AppCompatActivity {

    private TabLayout tabLayoutInfoBook;
    private AppBarLayout appBarLayoutInfoBook;
    private ViewPager viewPagerInfoBook;
    private TextView txtInfoNameBook, txtInfoBookDownload,txtInfoBookPrice;
    private ImageView imgInFoBookCover;
    private ImageButton imgbtnInfoBookFavor;
    private Toolbar actionToolbarInfoBook;
    private RatingBar ratingBar;
    BooksModel booksModel = new BooksModel();

    private boolean isFavor = false;  //Biến Test cho Favor

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
        InfoBookFragment infoBookFragment = newInstance(booksModel.getNXB(),booksModel.getTacGia(), booksModel.getDanhMuc(), booksModel.getNgonNgu());
        adapter.AddFragment(infoBookFragment,"Info");
        adapter.AddFragment(summaryBookFragment, "Summary");
        adapter.AddFragment(new CommentBookFragment(), "Comment");
        //adapter Setup
        viewPagerInfoBook.setAdapter(adapter);
        tabLayoutInfoBook.setupWithViewPager(viewPagerInfoBook);

        //Favorite Book
        imgbtnInfoBookFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFavor == false){
                    imgbtnInfoBookFavor.setImageResource(R.drawable.ic_favor);
                    isFavor = true;
                    Toast.makeText(InfoBookActivity.this,"You have added favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    imgbtnInfoBookFavor.setImageResource(R.drawable.ic_not_favor);
                    isFavor = false;
                    Toast.makeText(InfoBookActivity.this,"You have canceled your favorites",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button Back on toolbar
        actionToolbarInfoBook.setTitle("");
        setSupportActionBar(actionToolbarInfoBook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        ratingBar = findViewById(R.id.rateInfoBook);
        Glide.with(InfoBookActivity.this)
                .load(booksModel.getBiaSach())
                .into(imgInFoBookCover);
        txtInfoNameBook.setText(booksModel.getTenSach());
        txtInfoBookDownload.setText("Downloaded: "+booksModel.getSoNguoiMua());
        txtInfoBookPrice.setText("Price: " + booksModel.getGiaTien());
        ratingBar.setRating(booksModel.getDanhGia());


    }
    private void loadBundleData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            booksModel.setBiaSach(bundle.getString("anhBia", ""));
            booksModel.setTenSach(bundle.getString("tenSach", ""));
            booksModel.setSoNguoiMua(bundle.getLong("soNguoiMua",0));
            booksModel.setDanhGia(bundle.getFloat("danhGia",0));
            booksModel.setNoiDung(bundle.getString("noiDung"," "));
            booksModel.setGioiThieuSach(bundle.getString("gioiThieu", " "));
            booksModel.setGiaTien(bundle.getDouble("giaTien", 0));
            booksModel.setDanhMuc(bundle.getString("danhMuc",""));
            //booksModel.setNgayUpload(bundle.get("ngayUpload",""));
            booksModel.setTacGia(bundle.getString("tacGia",""));
            booksModel.setNXB(bundle.getString("NXB",""));
            booksModel.setNgonNgu(bundle.getString("ngonNgu",""));
            booksModel.setSoNguoiMua(bundle.getLong("soNguoiMua",0));
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

    // Thiếu Timestamp ngayUpload,
    public static InfoBookFragment newInstance(String NXB, String tacGia, String danhMuc, String ngonNgu){
        InfoBookFragment f = new InfoBookFragment();
        Bundle args = new Bundle();
        args.putString("NXB",NXB);
        args.putString("tacGia", tacGia);
        args.putString("danhMuc",danhMuc);
        args.putString("ngonNgu",ngonNgu);
        f.setArguments(args);
        return f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.mnuShare){
            Toast.makeText(InfoBookActivity.this,"SHARE",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
