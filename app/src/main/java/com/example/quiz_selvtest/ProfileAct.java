package com.example.quiz_selvtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_topbar);
        TextView title = (TextView)findViewById(R.id.profile_toolbar_text);
        title.setText("Profile");
        findViewById(R.id.profile_toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsAct.class);
        startActivity(intent);
    }
}
