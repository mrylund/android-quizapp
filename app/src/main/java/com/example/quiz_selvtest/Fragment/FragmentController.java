package com.example.quiz_selvtest.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quiz_selvtest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentController extends AppCompatActivity {
    int backButtonCount = 0;
    Context context = this;
    BottomNavigationView bottomnav;
    static int[][] states = new int[][] {
            new int[] {android.R.attr.state_checked},//checked
            new int[] {-android.R.attr.state_checked} // unchecked
    };

    static int[] colors = new int[] {
            Color.BLACK,
            Color.GRAY,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        backButtonCount = 0;

        // Change color on all the icons in the bottom navigation menu
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
        bottomnav.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    // Inspired by: https://stackoverflow.com/a/16383385
    @Override
    public void onBackPressed()
    {
        //TODO: Make it properly select the menu items - currently it doesn't change selected state.

        // Update the fragment stack, incase something has not been pushed yet
        getSupportFragmentManager().executePendingTransactions();

        // Pop the backstack
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStackImmediate();
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
            String name = "1";
            Fragment selectedF = new HomeFragment();
            backButtonCount = 0;

            // Switch on the menu item, then set the data for the item
            switch (menuItem.getItemId()) {
                case R.id.Home:
                    selectedF = new HomeFragment();
                    name = "1";
                    break;
                case R.id.courses:
                    selectedF = new My_coursesFrag();
                    name = "2";
                    break;
                case R.id.your_profile:
                    selectedF = new ProfileFragment();
                    name = "3";
                    break;
            }

            // Mulighed for at pop backstack for at undgå uendelig mange af de samme fragmenter i stack.
            // Fejl med at ullige antal i backstack ikke tracker.
            //getSupportFragmentManager().popBackStack(name, 1);

            // Opdater fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).addToBackStack(name).commit();
            return true;
        }
    };

    // Change the color of the icon
    public void changeIconTint( int iconNum, int colorRes){
        Drawable icon = ContextCompat.getDrawable(context,iconNum);
        assert icon != null;
        icon.setColorFilter(ContextCompat.getColor(context,colorRes), PorterDuff.Mode.SRC_IN);
    }
}
