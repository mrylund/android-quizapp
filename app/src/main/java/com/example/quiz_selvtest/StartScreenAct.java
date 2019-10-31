package com.example.quiz_selvtest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartScreenAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        Button button = findViewById(R.id.startscreenbutton2);
        Button button2 = findViewById(R.id.button);
        TextView register = findViewById(R.id.registerTextView);

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
    }


    public void playWithoutAccount(View view) {
        Intent intent = new Intent(this, NoAccountAct.class);
        startActivity(intent);
    }
}