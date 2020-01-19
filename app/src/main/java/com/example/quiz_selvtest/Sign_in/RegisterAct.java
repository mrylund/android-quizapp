package com.example.quiz_selvtest.Sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterAct extends AppCompatActivity {

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final Context context = this;
    private Dialog dialog;
    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final EditText emailToValidate = findViewById(R.id.registerMail);
        final EditText passwordToRegister = findViewById(R.id.registerPassword);
        final EditText passwordToConfirm = findViewById(R.id.repeatPassword);
        ImageView goBack = findViewById(R.id.goBackBTN);
        final Button register = findViewById(R.id.registerButton);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailToValidate.getText().toString().trim();
                String password = passwordToRegister.getText().toString().trim();
                String passwordConfirm = passwordToConfirm.getText().toString().trim();
                //Hello
                // TODO: 1/17/2020  Database kald til at tjekke om userName og email allerede eksistere i databasen
                if(!email.matches(emailPattern) || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() ){
                    initCustomDialog("UH-OH! Error in field!","Please verify your inputs, and try again!");
                    return;
                }

                if(!password.matches(passwordConfirm)){
                    initCustomDialog("UH-OH! Password differs!","The passwords you have typed in, do not match each other");
                }else{
                    createUser(email, password);
                }

            }
        });
    }

    public void createUser(String email, String password) {

        // Inspireret af: https://firebase.google.com/docs/auth/android/start/
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            doUserRegistered(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterAct.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void doUserRegistered(FirebaseUser user) {
        //TODO add stuff here :)
        startActivity(new Intent(RegisterAct.this, RegisterUserInfo.class).putExtra("user",user));
    }

    public void initCustomDialog(String title, String description){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText(title);
        TextView dialogDesc = dialog.findViewById(R.id.dialogText);
        dialogDesc.setText(description);
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
