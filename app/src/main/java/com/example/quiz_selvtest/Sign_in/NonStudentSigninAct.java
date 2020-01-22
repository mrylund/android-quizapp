package com.example.quiz_selvtest.Sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NonStudentSigninAct extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "NonStudentSigninAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_student_signin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ImageView goBackBTN = findViewById(R.id.goBackBTN);
        Button signIn = findViewById(R.id.button2);
        TextView register = findViewById(R.id.registerTextView2);
        TextView forgotpw = findViewById(R.id.forgotPWTextView2);

        EditText usernameET = findViewById(R.id.userName);
        EditText passwordET = findViewById(R.id.password);
        usernameET.setText("test@test.dk");
        passwordET.setText("test123");


        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(NonStudentSigninAct.this, FragmentController.class));
                EditText usernameET = findViewById(R.id.userName);
                EditText passwordET = findViewById(R.id.password);
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if (!username.equals("") && !password.equals("")) {
                    signIn(username, password);
                }else{
                    Toast.makeText(NonStudentSigninAct.this, "Fill out the info, and try again.",
                            Toast.LENGTH_SHORT).show();
                }
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

    public void signIn(String email, String password) {

        // Inspireret af: https://firebase.google.com/docs/auth/android/start/
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(NonStudentSigninAct.this, "Authentication Success!",
                            Toast.LENGTH_SHORT).show();
                    checkIfUserHasInfo();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(NonStudentSigninAct.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkIfUserHasInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            DocumentReference userInfo = FirebaseFirestore.getInstance().collection("Users").document(currentUser.getUid());
            userInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Intent intent = new Intent(NonStudentSigninAct.this, FragmentController.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(NonStudentSigninAct.this, RegisterUserInfo.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}
