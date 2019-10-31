package com.example.quiz_selvtest;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragMenue extends Fragment {


    public FragMenue() {
        // Required empty public constructor
    }
    public static FragMenue newinstance(){
        return new FragMenue();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_menue, container, false);
    }

}
