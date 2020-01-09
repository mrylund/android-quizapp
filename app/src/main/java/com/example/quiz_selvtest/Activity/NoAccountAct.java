package com.example.quiz_selvtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz_selvtest.R;

public class NoAccountAct extends AppCompatActivity {
    private String pass = "ABC123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_account);
        ImageView goBackBTN = findViewById(R.id.goBackBTN2);

        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoAccountAct.this, StartScreenAct.class));
            }
        });

        Button btnJoin = findViewById(R.id.btn_join_quiz);
        final EditText joinCode = findViewById(R.id.txt_joincode);

        joinCode.setText("ABC123");

        joinCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    joinQuiz(joinCode.getText().toString());
                }
                return true;
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinQuiz(joinCode.getText().toString());
            }
        });
    }

    public void joinQuiz(View view, String password) {
        joinQuiz(password);
    }

    public void joinQuiz(String password) {
        if (password.equals(pass)) {
            Intent intent = new Intent(this, Quiz.class);
            intent.putExtra("quizCode", password);
            startActivity(intent);
        } else {
            EditText joinCode = findViewById(R.id.txt_joincode);
        }



    }
}
