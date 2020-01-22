package com.example.quiz_selvtest.Sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quiz_selvtest.R;

public class StudentSigninAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signin);

        Button button1 = findViewById(R.id.btnSignin);
        ImageView goBackBTN = findViewById(R.id.btnGoBack);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentSigninAct.this, "Denne funktion er ikke implementeret endnu, kommer i en senere version.",
                        Toast.LENGTH_LONG).show();
                //startActivity( new Intent(StudentSigninAct.this, FragmentController.class));
            }
        });
        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
