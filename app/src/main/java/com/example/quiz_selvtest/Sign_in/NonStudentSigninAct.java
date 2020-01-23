package com.example.quiz_selvtest.Sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_selvtest.Fragment.FragmentController;
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

        ImageView goBackBTN = findViewById(R.id.btnGoBack);
        Button signIn = findViewById(R.id.btnSignin);
        TextView register = findViewById(R.id.txtRegister);
        TextView forgotpw = findViewById(R.id.txtForgotPW);

        EditText usernameET = findViewById(R.id.txtUsername);
        EditText passwordET = findViewById(R.id.txtPassword);

        passwordET.setOnEditorActionListener(editorActionListener);


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
                EditText usernameET = findViewById(R.id.txtUsername);
                EditText passwordET = findViewById(R.id.txtPassword);
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                // Check if both username and password has been entered
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

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                authentication();
            }
            return true;
        }
    };

    public void signIn(String email, String password) {

        // Inspireret af: https://firebase.google.com/docs/auth/android/start/
        // Sign the user in with the given email and password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(NonStudentSigninAct.this, "Authentication Success!",
                            Toast.LENGTH_SHORT).show();
                    checkIfUserHasInfo(); // Check if the user has already entered Username and Language
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(NonStudentSigninAct.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void authentication(){
        EditText usernameET = findViewById(R.id.txtUsername);
        EditText passwordET = findViewById(R.id.txtPassword);
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        // Check if both username and password has been entered
        if (!username.equals("") && !password.equals("")) {
            signIn(username, password);
        }else{
            Toast.makeText(NonStudentSigninAct.this, "Fill out the info, and try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void checkIfUserHasInfo() {
        // Check if user has entered any info when they registered.
        // If no info has been found, take them to the registerInfo screen, else take them to the home screen
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
