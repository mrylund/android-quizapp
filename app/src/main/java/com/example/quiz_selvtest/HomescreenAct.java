package com.example.quiz_selvtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class HomescreenAct extends AppCompatActivity {

    Boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        state = true;
        Display();
    }

    public void Display(){
        FragMenue menueOpen =FragMenue.newinstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(state) {
            fragmentTransaction.add(R.id.fragment_container, menueOpen).addToBackStack(null).commit();
            state = false;
        }else{

            FragMenue menueClose = (FragMenue) fragmentManager.findFragmentById(R.id.fragment_container);
            if (menueClose != null) {
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(menueClose).commit();
            }
            state = true;
        }
    }
}
