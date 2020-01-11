package com.example.quiz_selvtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz_selvtest.R;

import org.w3c.dom.Text;

public class StartScreenAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    public void playWithoutAccount(View view) {
        Intent intent = new Intent(this, NoAccountAct.class);
        startActivity(intent);
    }
}