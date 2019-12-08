package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Adapters.BookFindingAdapter;
import com.anhhung.greenbook.Fragments.HomeFragment;
import com.anhhung.greenbook.Fragments.LibraryFragment;
import com.anhhung.greenbook.Fragments.ProfileFragment;
import com.anhhung.greenbook.Models.BooksModel;
import com.anhhung.greenbook.Models.CategoriesModel;
import com.anhhung.greenbook.Models.UsersModel;
import com.anhhung.greenbook.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private BottomNavigationView navigationBottom;
    private FrameLayout navBottomFramelayout;
    private Toolbar actionToolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationDrawerView;
    View view;
    private TextView txtNameAccount;
    private HomeFragment homeFragment;
    private LibraryFragment libraryFragment;
    private ProfileFragment profileFragment;
    private CircleImageView imgDrawerProfile;
    private Dialog dialogNoConnect;

    private Intent intent;
    private String emailUser;
    private UsersModel user;

    private FirebaseFirestore db;
    FirebaseUser firebaseUser;
    private RecyclerView recyclerView;
    private BookFindingAdapter bookFindingAdapter;
    private List<BooksModel> booksModelsFindBook = new ArrayList<>();
    private List<BooksModel> booksModels = new ArrayList<>();
    private List<CategoriesModel> categoriesModels = new ArrayList<>();
    private MyCallback myCallback;
    private MyCallbackCategories myCallbackCategories;
    int count = 0;
    private boolean loadBoookSuccess = false;


    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        setFragment(homeFragment);
        loadUser();
        addEvents();
    }

    private void addControls() {
        intent = getIntent();
        emailUser = intent.getStringExtra("email");
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerFindBook);
        navBottomFramelayout = findViewById(R.id.navBottomFramelayout);
        navigationBottom = findViewById(R.id.navigationBottom);
        actionToolbar = findViewById(R.id.actionToolbar);

        homeFragment = new HomeFragment();
        libraryFragment = new LibraryFragment();
        profileFragment  = new ProfileFragment();

        //Navigation Drawer
        setSupportActionBar(actionToolbar);
        navigationDrawerView = findViewById(R.id.navigationDrawerView);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, R.string.navigationDrawer_Open, R.string.navigationDrawer_Close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        view =  navigationDrawerView.getHeaderView(0);
        txtNameAccount = view.findViewById(R.id.txtNameAccount);
        imgDrawerProfile = view.findViewById(R.id.imgDrawerProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        booksModels.clear();
        recyclerView.removeAllViews();
        categoriesModels.clear();
        readData2(new MyCallbackCategories() {
            @Override
            public void onCallback(final List<CategoriesModel> categoriesModels) {
                readData(new MyCallback() {
                    @Override
                    public void onCallback(List<BooksModel> abooksModels) {
                        booksModels = abooksModels;
                        createDataListView(recyclerView,booksModels);
                        booksModelsFindBook.addAll(booksModels);
                        loadBoookSuccess = true;
                    }
                });
            }
        });
    }

    private void loadUser() {
        db.collection("UserModel")
                .whereEqualTo("email", emailUser)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(UsersModel.class);
                                txtNameAccount.setText(user.getHoTen());
                                if (user.getHinhDaiDien() != null) {
                                    Glide.with(MainActivity.this)
                                            .load(user.getHinhDaiDien())
                                            .into(imgDrawerProfile);
                                    Log.d(TAG, user.getHoTen());
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void addEvents() {
        navigationBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_bottom_home:
                        setFragment(homeFragment);
                        actionToolbar.setTitle("Home");
                        return true;
                    case R.id.nav_bottom_library:
                        setFragment(libraryFragment);
                        actionToolbar.setTitle("Library");
                        return true;
                    case R.id.nav_bottom_profile:
                        setFragment(profileFragment);
                        actionToolbar.setTitle("Profile");
                        return true;
                    default:
                        return true;

                }
            }
        });

        navigationDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.drawer_home:
                        Toast.makeText(MainActivity.this, "Open Home",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_library:
                        Toast.makeText(MainActivity.this, "Open Library",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_profile:
                        Toast.makeText(MainActivity.this, "Open Profile",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        //Tạo đối tượng quản lý
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Tạo đối tượng thực hiện phiên tương tác.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navBottomFramelayout,fragment);
        //Xác nhận tương tác
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Search menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuSearch = menu.findItem(R.id.mnuSeach);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.removeAllViews();
                recyclerView.setVisibility(View.INVISIBLE);
                return false;

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.removeAllViews();
                recyclerView.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(loadBoookSuccess == true){
                    newText = newText.toLowerCase(Locale.getDefault());
                    booksModels.clear();
                    if (newText.length() == 0) {
                    } else {
                        for (BooksModel wp : booksModelsFindBook ) {
                            if (wp.getTenSach().toLowerCase(Locale.getDefault()).contains(newText)) {
                                booksModels.add(wp);
                            }
                        }
                    }
                    bookFindingAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                }
                return false;

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        recyclerView.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    //Find Book
    public interface MyCallback {
        void onCallback(List<BooksModel> booksModels);
    }

    public interface MyCallbackCategories {
        void onCallback(List<CategoriesModel> categoriesModels);
    }

    private void getAllCategoriesName() {
        try {
            db.collection("DanhMucCollection")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    CategoriesModel categoriesModel;
                                    categoriesModel = document.toObject(CategoriesModel.class);
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    categoriesModels.add(categoriesModel);
                                }
                                myCallbackCategories.onCallback(categoriesModels);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("ERR", e.toString());
        }
    }

    public void getAllDocumentsInDanhMucCollection() {
        for (int i = 0; i < categoriesModels.size(); i++) {
            try {
                db.collection("DanhMucCollection").document(categoriesModels.get(i).getId()).collection("SachColection")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        BooksModel booksModel;
                                        booksModel = document.toObject(BooksModel.class);
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        booksModels.add(booksModel);
                                    }
                                    count++;
                                    if (count - 1 == categoriesModels.size() - 1) {
                                        myCallback.onCallback(booksModels);
                                        count = 0;
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception e) {
                Log.d("ERR", e.toString());
            }
        }

    }

    public void readData2(MyCallbackCategories myCallbackCategories) {
        this.myCallbackCategories = myCallbackCategories;
        getAllCategoriesName();
    }

    public void readData(MyCallback myCallback) {
        this.myCallback = myCallback;
        getAllDocumentsInDanhMucCollection();
    }
    private void createDataListView(RecyclerView recyclerView, List<BooksModel> booksModels){
        bookFindingAdapter = new BookFindingAdapter(this,booksModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookFindingAdapter);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        if(isNetworkAvailable() == false){
            dialogNoConnect = new Dialog(view.getContext());
            dialogNoConnect.setContentView(R.layout.dialog_no_internet);
            dialogNoConnect.setCancelable(false);
            dialogNoConnect.show();
            Button btnYesConnect, btnNoConnect;
            btnYesConnect = dialogNoConnect.findViewById(R.id.btnYesConnect);
            btnYesConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isNetworkAvailable() == false){
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }  else {
                        dialogNoConnect.dismiss();
                    }
                }
            });
        }
        super.onResume();
    }
}