package com.example.quiz_selvtest.Sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.R;

public class RegisterAct extends AppCompatActivity {

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean allAccepted = false;
    private boolean emailAccepted = false;
    private boolean usernameAccepted = false;
    private boolean passwordAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView goBack = findViewById(R.id.goBackBTN);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void emailAccepted(){
        final EditText emailToValidate = findViewById(R.id.registerMail);
        final String email = emailToValidate.getText().toString().trim();

        emailToValidate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.matches(emailPattern) && s.length() > 0){
                    emailAccepted = true;
                }
            }
        });
    }

    private void isPasswordAccepted(){
        final EditText passWordToValidate = findViewById(R.id.registerPassword);
        final EditText repeatPassword = findViewById(R.id.repeatPassword);

        String pw = passWordToValidate.getText().toString().trim();
        String repeatPW = repeatPassword.getText().toString().trim();



    }
}
