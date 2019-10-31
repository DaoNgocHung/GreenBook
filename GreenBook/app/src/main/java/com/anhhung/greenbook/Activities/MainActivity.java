package com.anhhung.greenbook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhhung.greenbook.Fragments.HomeFragment;
import com.anhhung.greenbook.Fragments.LibraryFragment;
import com.anhhung.greenbook.Fragments.ProfileFragment;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBottom;
    private FrameLayout navBottomFramelayout;
    private Toolbar actionToolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationDrawerView;
    View view;
    private TextView txtNameAccount, txtMoneyAccount;
    private HomeFragment homeFragment;
    private LibraryFragment libraryFragment;
    private ProfileFragment profileFragment;
    private CircleImageView imgDrawerProfile;

    Intent intent;
    private String emailUser;
    private UsersModel user;

    FirebaseFirestore db;
    FirebaseUser firebaseUser;


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
        txtMoneyAccount = view.findViewById(R.id.txtMoneyAccount);
        imgDrawerProfile = view.findViewById(R.id.imgDrawerProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                                txtMoneyAccount.setText(user.getTien().toString());
                                if(user.getHinhDaiDien() != null){
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

//        db.collection("UserModel")
//                .whereEqualTo("id",firebaseUser.getUid())
//                .limit(1)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                user = document.toObject(UsersModel.class);
//                                txtNameAccount.setText(user.getHoTen());
//                                txtMoneyAccount.setText(user.getTien().toString());
//                                Glide.with(MainActivity.this)
//                                        .load(user.getHinhDaiDien())
//                                        .into(imgDrawerProfile);
//                                Log.d(TAG, user.getHoTen());
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });


//        ImageView imgDrawerProfile = view.findViewById(R.id.imgDrawerProfile);
//        imgDrawerProfile.setImageResource();


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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
