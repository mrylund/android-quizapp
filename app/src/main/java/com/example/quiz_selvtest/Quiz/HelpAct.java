package com.example.quiz_selvtest.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz_selvtest.R;

public class HelpAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);

        ImageView goBackBTN = findViewById(R.id.goBackBTNhelp);
        TextView link = findViewById(R.id.templateLink);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
