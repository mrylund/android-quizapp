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
import com.example.quiz_selvtest.Sign_in.RegisterUserInfo;
import com.example.quiz_selvtest.Sign_in.StudentSigninAct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
            DocumentReference userInfo = FirebaseFirestore.getInstance().collection("Users").document(currentUser.getUid());
            userInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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