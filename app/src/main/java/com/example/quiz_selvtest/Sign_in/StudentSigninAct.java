package com.example.quiz_selvtest.Sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.R;

public class StudentSigninAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signin);

        Button button1 = findViewById(R.id.button2);
        ImageView goBackBTN = findViewById(R.id.goBackBTN3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(StudentSigninAct.this, FragmentController.class));
            }
        });
        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentSigninAct.this, StartScreenAct.class));
            }
        });

    }
}
