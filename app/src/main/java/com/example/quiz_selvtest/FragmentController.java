package com.example.quiz_selvtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlistner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedF = null;
            switch (menuItem.getItemId()){

                case R.id.Home:
                    selectedF = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).commit();
                    break;
                case R.id.courses:
                    selectedF = new My_coursesFrag();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).commit();
                    break;
                case R.id.your_profile:
                    Intent intent = new Intent(FragmentController.this, ProfileAct.class);
                    startActivity(intent);
                    break;
            }
            return true;

        }
    };

}
