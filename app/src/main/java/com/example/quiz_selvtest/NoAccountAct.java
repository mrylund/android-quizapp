package com.example.quiz_selvtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NoAccountAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_account);
    }

    public void joinQuiz(View view) {
        Intent intent = new Intent(this, Quiz_1.class);
        startActivity(intent);
    }
}
