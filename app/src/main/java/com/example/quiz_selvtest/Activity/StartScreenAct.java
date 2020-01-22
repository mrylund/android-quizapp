package com.example.quiz_selvtest.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.R;
import com.example.quiz_selvtest.Sign_in.ForgotPWAct;
import com.example.quiz_selvtest.Sign_in.NoAccountAct;
import com.example.quiz_selvtest.Sign_in.NonStudentSigninAct;
import com.example.quiz_selvtest.Sign_in.RegisterAct;
import com.example.quiz_selvtest.Sign_in.StudentSigninAct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartScreenAct extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_startscreen);

        // Find objects and save them in variables, then define the onClick methods
        Button btnNonStudent = findViewById(R.id.nonStudentBtn);
        Button btnStudentTeacher = findViewById(R.id.studentTeacherBtn);
        TextView txtRegister = findViewById(R.id.registerTxt);
        TextView txtForgotPW = findViewById(R.id.forgotPWTextView);


        btnStudentTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, StudentSigninAct.class));
            }
        });

        btnNonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, NonStudentSigninAct.class));
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, RegisterAct.class));
            }
        });
        txtForgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenAct.this, ForgotPWAct.class));
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if  a user is signed in (user object Not-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            DocumentReference userInfo = FirebaseFirestore.getInstance().collection("Users").document(currentUser.getUid());
            userInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    // If a user is signed in already, then redirect the user to the home screen
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Intent intent = new Intent(StartScreenAct.this, FragmentController.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }


    public void playWithoutAccount(View view) {
        Intent intent = new Intent(this, NoAccountAct.class);
        startActivity(intent);
    }
}