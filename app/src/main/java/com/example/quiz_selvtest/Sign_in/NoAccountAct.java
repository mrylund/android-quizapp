package com.example.quiz_selvtest.Sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz_selvtest.Quiz.Quiz;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class NoAccountAct extends AppCompatActivity {
    private String pass = "ABC123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_account);
        ImageView goBackBTN = findViewById(R.id.goBackBTN9);

        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnJoin = findViewById(R.id.btn_join_quiz);
        final EditText joinCode = findViewById(R.id.txt_joincode);

        joinCode.setText("ABC123");

        // TODO Make this work
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


    public void joinQuiz(View v) {
        EditText joinCode = findViewById(R.id.txt_joincode);
        joinQuiz(joinCode.getText().toString());
    }

    public void joinQuiz(String pw) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("Quizzes");
        Query query = col.whereEqualTo("ID", pw);
        final Activity act = this;
        query.limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = Objects.requireNonNull(task.getResult()).getDocuments();
                    if (documents.size() < 1) return;
                    DocumentSnapshot document = documents.get(0);
                    Intent intent = new Intent(act, Quiz.class);

                    String sheet = (String)document.get("Sheet");

                    intent.putExtra("sheet", sheet);
                    startActivity(intent);
                }
            }
        });
    }
}
