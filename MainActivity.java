package com.txt.worldyachts_mobile;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.txt.worldyachts_mobile.api.Customers;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    public static Customers user;
    public static boolean isGuest = true;

    public static int getImageById(int id, int num) {
        switch (id) {
            case 101: {
                switch (num) {
                    case 1:
                        return R.mipmap.y101_1;
                    case 2:
                        return R.mipmap.y101_2;
                    case 3:
                        return R.mipmap.y101_3;
                }
            }
            case 201: {
                switch (num) {
                    case 1:
                        return R.mipmap.y201_1;
                    case 2:
                        return R.mipmap.y201_2;
                    case 3:
                        return R.mipmap.y201_3;
                }
            }
            case 202: {
                switch (num) {
                    case 1:
                        return R.mipmap.y202_1;
                    case 2:
                        return R.mipmap.y202_2;
                    case 3:
                        return R.mipmap.y202_3;
                }
            }
            case 301: {
                switch (num) {
                    case 1:
                        return R.mipmap.y301_1;
                    case 2:
                        return R.mipmap.y301_2;
                    case 3:
                        return R.mipmap.y301_3;
                }
            }
            case 302: {
                switch (num) {
                    case 1:
                        return R.mipmap.y302_1;
                    case 2:
                        return R.mipmap.y302_2;
                    case 3:
                        return R.mipmap.y302_3;
                }
            }
            case 303: {
                switch (num) {
                    case 1:
                        return R.mipmap.y303_1;
                    case 2:
                        return R.mipmap.y303_2;
                    case 3:
                        return R.mipmap.y303_3;
                }
            }
        }

        return id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main, R.id.nav_order).setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
