package com.example.quiz_selvtest.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.quiz_selvtest.R;

import java.io.IOException;

public class Quiz extends AppCompatActivity {
    private QuizHandler game;
    Intent intent;

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
            getIntent();
            String quizCode = intent.getStringExtra("quizCode");
            setQuestion("Indlæser spørgsmål");
            try {
                game = new QuizHandler(quizCode);
                status = "Success";
                System.out.println(game.getQuestion());
            } catch (IOException e) {
                e.printStackTrace();
                status = "Fail";
            }

            for (int i = 1; i <= 9; i++) {
                try {
                    Thread.sleep(320);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i%4);
            }




            return status;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case 0: setQuestion("Indlæser spørgsmål"); break;
                case 1: setQuestion("Indlæser spørgsmål."); break;
                case 2: setQuestion("Indlæser spørgsmål.."); break;
                case 3: setQuestion("Indlæser spørgsmål..."); break;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Success")) {
                setInfo();
                findViewById(R.id.Quiz_btn1).setVisibility(View.VISIBLE);
                findViewById(R.id.Quiz_btn2).setVisibility(View.VISIBLE);
                findViewById(R.id.Quiz_btn3).setVisibility(View.VISIBLE);
                findViewById(R.id.Quiz_btn4).setVisibility(View.VISIBLE);
                findViewById(R.id.Quiz_question_count).setVisibility(View.VISIBLE);

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
        String[] ans = game.getAnswers();

        String qNum = String.format(getString(R.string.question_number), game.getCurQuestionNum() + 1, game.getQuestionCount());

        TextView questionNum = findViewById((R.id.Quiz_question_count));
        questionNum.setText(qNum);

        int n = 0;
        for (String a : ans) {
            n++;
            setAnswer(n, a);
        }

        setQuestion(q);
    }

    private void pressAnswer(int n) {
        if (game.checkAnswer(n, false)) {
            game.addScore();
        }
        game.nextQuestion();
        setInfo();

        if (game.hasEnded()) {
            endGame();
        }
    }

    private void endGame() {
        Intent intent = new Intent(this, Quiz_done.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }


    private void setQuestion(String a) {
        TextView q = findViewById((R.id.Quiz_Question));
        q.setText(a);
    }

    private void setAnswer(final int n, String a) {
        Button btn = findViewById(R.id.Quiz_btn1);
        switch(n) {
            case 1: btn = findViewById(R.id.Quiz_btn1); break;
            case 2: btn = findViewById(R.id.Quiz_btn2); break;
            case 3: btn = findViewById(R.id.Quiz_btn3); break;
            case 4: btn = findViewById(R.id.Quiz_btn4); break;
        }
        btn.setText(a);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressAnswer(n);
            }
        });
    }

}
