package com.example.demolightbook.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.demolightbook.Fragments.AccountFragment;
import com.example.demolightbook.Fragments.LibraryFragment;
import com.example.demolightbook.Fragments.StoreFragment;
import com.example.demolightbook.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    FrameLayout nav_frame;

    private StoreFragment storeFragment;
    private LibraryFragment libraryFragment;
    private AccountFragment accountFragment;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView nav_drawer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        setFragment(storeFragment);
        addEvents();
    }


    private void addControls() {
        //Navigation Bottom
        navigation = findViewById(R.id.navigation);
        nav_frame = findViewById(R.id.nav_frame);

        storeFragment = new StoreFragment();
        libraryFragment = new LibraryFragment();
        accountFragment = new AccountFragment();

        //Navigation Drawer
        toolbar = findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);
        nav_drawer_view = findViewById(R.id.nav_drawer_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addEvents() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_store:
                        setFragment(storeFragment);
                        return true;
                    case R.id.nav_library:
                        setFragment(libraryFragment);
                        return true;
                    case R.id.nav_account:
                        setFragment(accountFragment);
                        return true;
                    default:
                        return true;

                }
            }
        });

        nav_drawer_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id) {
                    case R.id.drawer_dashboard:
                        Toast.makeText(MainActivity.this, "Open DASHBOARD",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_notification:
                        Toast.makeText(MainActivity.this, "Open NOTIFICATION",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_settings:
                        Toast.makeText(MainActivity.this, "Open SETTINGS",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_events:
                        Toast.makeText(MainActivity.this, "Open EVENTS",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_info:
                        Toast.makeText(MainActivity.this, "Open INFORMATION",Toast.LENGTH_SHORT).show();break;
                    case R.id.drawer_logout:
                        Toast.makeText(MainActivity.this, "LOG OUT",Toast.LENGTH_SHORT).show();break;
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
        fragmentTransaction.replace(R.id.nav_frame,fragment);
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
