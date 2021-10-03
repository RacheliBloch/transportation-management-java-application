package com.example.myapplication2.UI.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;

import com.example.myapplication2.R;
import com.example.myapplication2.UI.ServiceAndBr.MyBroadcastReceiver;
import com.example.myapplication2.UI.ServiceAndBr.MyService;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavigationActivity extends AppCompatActivity {

    DrawerLayout drawer ;
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView nv;
    String ContentUserMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawer= findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("NEWTRAVEL");
        registerReceiver(new MyBroadcastReceiver(), intentFilter);
        startService(new Intent(NavigationActivity.this,MyService.class));
        setSupportActionBar(toolbar);
        ContentUserMail=getIntent().getStringExtra("MY_MAIL");
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_companytravels, R.id.nav_registeredtravels, R.id.nav_historytravels)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public String getMyData() {
        return ContentUserMail ;
    }
}