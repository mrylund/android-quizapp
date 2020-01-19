package com.example.quiz_selvtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.R;
import com.example.quiz_selvtest.Sign_in.ForgotPWAct;
import com.example.quiz_selvtest.Sign_in.NoAccountAct;
import com.example.quiz_selvtest.Sign_in.NonStudentSigninAct;
import com.example.quiz_selvtest.Sign_in.RegisterAct;
import com.example.quiz_selvtest.Sign_in.StudentSigninAct;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StartScreenAct extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //URL reference to our database. Everything will be stored in the link below.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://quizr-c3c3b.firebaseio.com/");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_startscreen);

        Button button = findViewById(R.id.startscreenbutton2);
        Button button2 = findViewById(R.id.button);
        TextView register = findViewById(R.id.registerTextView);
        TextView forgotpw = findViewById(R.id.forgotPWTextView);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, StudentSigninAct.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, NonStudentSigninAct.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, RegisterAct.class));
            }
        });
        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, ForgotPWAct.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(StartScreenAct.this, FragmentController.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }


    public void playWithoutAccount(View view) {
        Intent intent = new Intent(this, NoAccountAct.class);
        startActivity(intent);
    }
}