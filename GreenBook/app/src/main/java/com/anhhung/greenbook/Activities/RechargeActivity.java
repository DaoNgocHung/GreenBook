package com.anhhung.greenbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.anhhung.greenbook.R;

public class RechargeActivity extends AppCompatActivity {

    private Toolbar actionToolbarRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        addControls();
        addEvents();
    }

    private void addControls() {
        actionToolbarRecharge = findViewById(R.id.actionToolbarRecharge);
        actionToolbarRecharge.setTitle("Recharge");
    }

    private void addEvents() {
        //Back toolbar
        setSupportActionBar(actionToolbarRecharge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
