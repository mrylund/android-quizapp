package com.example.quiz_selvtest.Sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.R;

public class NonStudentSigninAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_student_signin);

        ImageView goBackBTN = findViewById(R.id.goBackBTN2);
        Button signIn = findViewById(R.id.button2);
        TextView register = findViewById(R.id.registerTextView2);
        TextView forgotpw = findViewById(R.id.forgotPWTextView2);

        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NonStudentSigninAct.this, StartScreenAct.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NonStudentSigninAct.this, FragmentController.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NonStudentSigninAct.this, RegisterAct.class));
            }
        });
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NonStudentSigninAct.this, ForgotPWAct.class));
            }
        });

    }
}
