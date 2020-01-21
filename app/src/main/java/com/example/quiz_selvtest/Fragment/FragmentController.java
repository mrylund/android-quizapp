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
        changeIconTint(R.drawable.profile,R.color.black);
        changeIconTint(R.drawable.books,R.color.colorPrimaryDark);
        changeIconTint(R.drawable.homelogo,R.color.darkGrey);
        ColorStateList colorList = new ColorStateList(states, colors);
        bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setSelectedItemId(R.id.Home);
        bottomnav.setItemTextColor(colorList);
        backButtonCount = 0;

        bottomnav.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    // Inspired by: https://stackoverflow.com/a/16383385
    @Override
    public void onBackPressed()
    {
        getSupportFragmentManager().executePendingTransactions();
        System.out.println("ANTAL: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStackImmediate();
//            Fragment selectedF = new HomeFragment();
//            bottomnav.setSelectedItemId(R.id.Home);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
            return;
        }

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
            String name;
            Fragment selectedF;
            backButtonCount = 0;
            switch (menuItem.getItemId()){
                case R.id.Home:
                    selectedF = new HomeFragment();
                    name = "1";
                    getSupportFragmentManager().popBackStack(name, 1);

                    //changeIconTint(R.drawable.profile,R.color.dark);
                    findViewById(R.id.bottom_navigation).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).addToBackStack(name).commit();
                    //return true;
                    break;
                case R.id.courses:
                    selectedF = new My_coursesFrag();
                    name = "2";
                    //getSupportFragmentManager().popBackStack(name, 1);
                    //changeIconTint(R.drawable.books,R.color.colorPrimaryDark);
                    findViewById(R.id.bottom_navigation).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).addToBackStack(name).commit();
                    //return true;
                    break;
                case R.id.your_profile:
                    selectedF = new ProfileFragment();
                    name = "3";
                    //getSupportFragmentManager().popBackStack(name, 1);
                    //changeIconTint(R.drawable.profile,  R.color.dark);
                    findViewById(R.id.bottom_navigation).setBackgroundColor(Color.WHITE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedF).addToBackStack(name).commit();
                    break;
                    //return true;
            }
            return true;
        }
    };

    public void changeIconTint( int drawableRes1, int colorRes){
       /* int drawableRes2, int drawableRes3,*/
        Drawable drawable1 = ContextCompat.getDrawable(context,drawableRes1);
        assert drawable1 != null;
        drawable1.setColorFilter(ContextCompat.getColor(context,colorRes), PorterDuff.Mode.SRC_IN);

       /* Drawable drawable2 = ContextCompat.getDrawable(context,drawableRes2);
        assert drawable2 != null;
        drawable2.setColorFilter(ContextCompat.getColor(context,colorRes), PorterDuff.Mode.SRC_IN);

        Drawable drawable3 = ContextCompat.getDrawable(context,drawableRes3);
        assert drawable3 != null;
        drawable3.setColorFilter(ContextCompat.getColor(context,colorRes), PorterDuff.Mode.SRC_IN);*/

    }

    public void changeIconTitleColor(){
        /* Fået inspiration herfra: https://stackoverflow.com/questions/15543186/how-do-i-create-colorstatelist-programmatically*/
        bottomnav.getSelectedItemId();

    }
}
