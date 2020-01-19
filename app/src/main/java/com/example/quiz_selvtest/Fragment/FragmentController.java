package com.example.quiz_selvtest.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quiz_selvtest.Activity.ProfileAct;
import com.example.quiz_selvtest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentController extends AppCompatActivity {
    int backButtonCount = 0;
    BottomNavigationView bottomnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        backButtonCount = 0;

        bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setSelectedItemId(R.id.Home);
        bottomnav.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    // Inspired by: https://stackoverflow.com/a/16383385
    @Override
    public void onBackPressed()
    {
        if (bottomnav.getSelectedItemId() != R.id.Home) {
            Fragment selectedF = new HomeFragment();
            bottomnav.setSelectedItemId(R.id.Home);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
            return;
        }

        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backButtonCount = 0;
            //android.os.Process.killProcess(android.os.Process.myPid());
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedF;
            switch (menuItem.getItemId()){
                case R.id.Home:
                    selectedF = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).commit();
                    return true;
//                    break;
                case R.id.courses:
                    selectedF = new My_coursesFrag();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).commit();
                    return true;
//                    break;
                case R.id.your_profile:
                    selectedF = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).commit();
                    return true;

//                    Intent intent = new Intent(FragmentController.this, ProfileAct.class);
//                    startActivity(intent);
//                    return true;
////                    break;
            }
            return false;

        }
    };

}
