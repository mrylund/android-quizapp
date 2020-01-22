package com.example.quiz_selvtest.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.quiz_selvtest.R;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public class Quiz extends AppCompatActivity {
    private QuizHandler game;
    Intent intent;
    private boolean a1 = false, a2 = false, a3 = false, a4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        intent = getIntent();
        new StartQuiz().execute("");
    }

    private class StartQuiz extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String status;
            String sheetID = intent.getStringExtra("sheet");
            setQuestion("Loading questions");

            // Attempt to load the questions from google sheets
            try {
                game = new QuizHandler(sheetID);
                status = "Success";
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
                status = "Fail";
            }

            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Success")) {
                setInfo(); // Set the information in the quiz: Questions, answers and correct answers
                findViewById(R.id.Quiz_question_count).setVisibility(View.VISIBLE);
                Button answer = findViewById(R.id.btnAnswer);
                answer.setVisibility(View.VISIBLE);
                answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answer();
                    }
                });

                // Remove the loading animation
                LottieAnimationView loading = findViewById(R.id.loadingAnim);
                loading.setVisibility(View.GONE);
            } else {
                // If no internet connection the app will fail.
                setQuestion("Something went wrong");
            }
        }
    }

    private void setInfo() {
        String q = game.getQuestion();
        List<String> ans = game.getAnswers();

        // Check if at least 2 answers
        if (ans.get(0).equals("") || ans.get(1).equals("")) {
            game.nextQuestion();
            if (game.hasEnded()) {
                endGame();
            }
            setInfo();
            return;
        }

        // Set the current question info text
        String qNum = String.format(getString(R.string.question_number), game.getCurQuestionNum() + 1, game.getQuestionCount());
        TextView questionNum = findViewById((R.id.Quiz_question_count));
        questionNum.setText(qNum);

        a1 = false;
        a2 = false;
        a3 = false;
        a4 = false;

        // Set all answer possibilities
        int n = 0;
        for (String a : ans) {
            n++;
            setAnswer(n, a);
        }

        // Set the question
        setQuestion(q);
    }

    private void pressAnswer(View v, int n) {
        // Keep track of what buttons are pressed
        switch (n) {
            case 1: a1 = !a1; break;
            case 2: a2 = !a2; break;
            case 3: a3 = !a3; break;
            case 4: a4 = !a4; break;
        }

    }

    private void answer() {
        // Check if all answers are correct, if they are add a point to the total score
        if (game.checkAnswers(a1, a2, a3, a4)) {
            game.addScore();
        }

        // Go to next question and update everything, end the game if no more questions
        game.nextQuestion();
        setInfo();
        if (game.hasEnded()) {
            endGame();
        }
    }

    private void endGame() {
        // Go to the end activity
        Intent intent = new Intent(this, Quiz_done.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }


    private void setQuestion(String a) {
        TextView q = findViewById((R.id.Quiz_Question));
        q.setText(a);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setAnswer(final int n, String a) {
        Button btn = findViewById(R.id.Quiz_btn1);
        switch(n) {
            case 1: btn = findViewById(R.id.Quiz_btn1); break;
            case 2: btn = findViewById(R.id.Quiz_btn2); break;
            case 3: btn = findViewById(R.id.Quiz_btn3); break;
            case 4: btn = findViewById(R.id.Quiz_btn4); break;
        }
        btn.setPressed(false);

        // If the answer has no text, remove the button
        if (a.equals("")) {
            btn.setVisibility(View.GONE);
            return;
        }

        // Make the button visible if it is valid, and update the untouch method
        btn.setVisibility(View.VISIBLE);
        btn.setText(a);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Button b = ((Button) v);
                    b.setPressed(!b.isPressed());//if pressed, unpress; if unpressed, press
                    pressAnswer(v, n);
                }
                return true;
            }
        });
    }

}
