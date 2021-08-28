package com.example.heritagehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Dashboard_fragment_container,
                    new Homefragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if(bottomNav.getSelectedItemId()==R.id.nav_home){
            super.onBackPressed();
            finish();
        }
        else if(bottomNav.getSelectedItemId()==R.id.nav_favorites){
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
        else
        {
            bottomNav.setSelectedItemId(R.id.nav_favorites);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Homefragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new DeveloperFragment();
                            break;
                    }



                    getSupportFragmentManager().beginTransaction().replace(R.id.Dashboard_fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}