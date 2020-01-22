package com.example.quiz_selvtest.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quiz_selvtest.Fragment.my_quizzes.MyQuiz;
import com.example.quiz_selvtest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


// Inspired by: https://droidmentor.com/bottomnavigationview-with-viewpager-android/
public class FragmentController extends AppCompatActivity {
    int backButtonCount = 0;
    Context context = this;
    BottomNavigationView bottomnav;
    ProfileFragment profileFragment;
    HomeFragment homeFragment;
    My_coursesFrag coursesFragment;
    ViewPager viewPager;
    MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        backButtonCount = 0;

        bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navlistner);

        /*// Change color on all the icons in the bottom navigation menu
        changeIconTint(R.drawable.profile,R.color.darkGrey);
        changeIconTint(R.drawable.books,R.color.darkGrey);
        changeIconTint(R.drawable.homelogo,R.color.darkGrey);

        // Change color on the text in the bottom navigation menu
        ColorStateList colorList = new ColorStateList(states, colors);

        // Change what menu item that is selected
        bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setSelectedItemId(R.id.Home);
        bottomnav.setItemTextColor(colorList);

        // Set navigation listener and set the fragment
        bottomnav.setOnNavigationItemSelectedListener(navlistner);*/
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomnav.getMenu().getItem(1).setChecked(false);

                bottomnav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomnav.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(1);
    }

    // Inspired by: https://stackoverflow.com/a/16383385
    @Override
    public void onBackPressed()
    {
        if (viewPager.getCurrentItem() != 1) {
            viewPager.setCurrentItem(1);
            return;
        }


        // If you press the back button when nothing is in the backstack, notify the user if they press
        // back again it will close the app.
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backButtonCount = 0;
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
            backButtonCount = 0;

            // Switch on the menu item, then set the data for the item
            switch (menuItem.getItemId()) {
                case R.id.Home:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.courses:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.your_profile:
                    viewPager.setCurrentItem(0);
                    break;
            }
            return false;
        }
    };

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        profileFragment =new ProfileFragment();
        homeFragment =new HomeFragment();
        coursesFragment =new My_coursesFrag();
        adapter.addFragment(profileFragment);
        adapter.addFragment(homeFragment);
        adapter.addFragment(coursesFragment);
        viewPager.setAdapter(adapter);
    }
}
