package com.example.mybus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
   // Button userprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        //toolbar
        setSupportActionBar(toolbar);
        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_users){
            Toast.makeText(this, "Navigation user clicked!", Toast.LENGTH_SHORT).show();
            Log.v("DashboardActivity","nav user clicked");
            Intent intent = new Intent(DashboardActivity.this,UserInfo.class);
            startActivity(intent);
        }
        return true;
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_map:
                Toast.makeText(this, "Navigation Map clicked!", Toast.LENGTH_SHORT).show();
                Log.v("DashboardActivity","nav map clicked");
                Intent intent1 = new Intent(DashboardActivity.this,MapsActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_users:
                Toast.makeText(this, "Navigation User clicked!", Toast.LENGTH_SHORT).show();
                Log.v("DashboardActivity","nav user clicked");
                Intent intent2 = new Intent(DashboardActivity.this,UserInfo.class);
                startActivity(intent2);
                break;
            case R.id.nav_ticket:
                Toast.makeText(this, "Navigation Payment clicked!", Toast.LENGTH_SHORT).show();
                Log.v("DashboardActivity","nav payment clicked");
                Intent intent3 = new Intent(DashboardActivity.this, LocationActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_location:
                Toast.makeText(this, "Navigation My location clicked!", Toast.LENGTH_SHORT).show();
                Log.v("DashboardActivity","nav my location clicked");
                Intent intent4 = new Intent(DashboardActivity.this,MyLocation.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Navigation Logout clicked!", Toast.LENGTH_SHORT).show();
                Log.v("DashboardActivity","nav logout clicked");
                Intent intent5 = new Intent(DashboardActivity.this,AuthActivity.class);
                startActivity(intent5);
                break;

        }
        return true;
    }
}