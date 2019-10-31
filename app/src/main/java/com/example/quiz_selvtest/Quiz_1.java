package com.example.quiz_selvtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_1);
    }


    public void checkAnswer(View view) {
        Intent intent = new Intent(this, Quiz_2.class);
        startActivity(intent);
    }
}
