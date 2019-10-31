package com.example.quiz_selvtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Quiz_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_2);
    }


    public void checkAnswer(View view) {
        Intent intent = new Intent(this, Quiz_done.class);
        startActivity(intent);
    }
}
