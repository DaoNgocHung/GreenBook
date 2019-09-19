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

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.anhhung.greenbook.Fragments.HomeFragment;
import com.anhhung.greenbook.Fragments.LibraryFragment;
import com.anhhung.greenbook.Fragments.ProfileFragment;
import com.anhhung.greenbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationBottom;
    private FrameLayout navBottomFramelayout;
    private Toolbar actionToolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationDrawerView;

    private HomeFragment homeFragment;
    private LibraryFragment libraryFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        setFragment(homeFragment);
        addEvents();
    }

    private void addControls() {
        navBottomFramelayout = findViewById(R.id.navBottomFramelayout);
        navigationBottom = findViewById(R.id.navigationBottom);
        actionToolbar = findViewById(R.id.actionToolbar);

        homeFragment = new HomeFragment();
        libraryFragment = new LibraryFragment();
        profileFragment  = new ProfileFragment();

        //Navigation Drawer
        actionToolbar = findViewById(R.id.actionToolbar);
        setSupportActionBar(actionToolbar);
        navigationDrawerView = findViewById(R.id.navigationDrawerView);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, R.string.navigationDrawer_Open, R.string.navigationDrawer_Close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorLimeGreen));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
