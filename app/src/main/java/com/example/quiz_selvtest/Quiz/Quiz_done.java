package com.example.quiz_selvtest.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.R;

public class Quiz_done extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_done);
        intent = getIntent();
        setInfo();
    }

    // Get the game object and set all the info in the page
    public void setInfo() {
        QuizHandler game = (QuizHandler) intent.getSerializableExtra("game");
        assert game != null;
        int score = game.getScore();
        int totalQuestions = game.getQuestionCount();

        TextView info = findViewById(R.id.Quiz_Done_Text);
        String text = info.getText().toString();
        text = String.format(text, score, totalQuestions);

        info.setText(text);
    }


    public void quizEnd(View view) {
        Intent intent = new Intent(this, StartScreenAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
