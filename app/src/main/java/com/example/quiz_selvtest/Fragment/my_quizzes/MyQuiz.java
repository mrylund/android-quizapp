package com.example.quiz_selvtest.Fragment.my_quizzes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MyQuiz extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Save view in a variable and retrieve the list
        setContentView(R.layout.myquizzes_fragment);
        ListView quizListView = findViewById(R.id.Quizzes_list);
        ImageView goBackBTN = findViewById(R.id.btnGoBack);

        goBackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Get all the quizzes a user has created and make a new ArrayAdapter containing the returned quizzes.
        ArrayList<Quiz> quizzes = getUserQuizzes();
        QuizAdapter adapter = new QuizAdapter(MyQuiz.this, R.layout.myquizzes_line, quizzes);

        // Set the list adapter
        quizListView.setAdapter(adapter);
    }


    // Inspirered by : https://stackoverflow.com/a/53606612
    private ArrayList<Quiz> getUserQuizzes() {
        ArrayList<Quiz> quizList = new ArrayList<>();

        // Initialize user authentication object
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Make a query to get all the quizzes a user created
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("Quizzes");
        Query query = col.whereEqualTo("Creator", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        ArrayList<Quiz> finalQuizList = quizList;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    // Loop through all the documents retrieved from the query and make Quiz objects from these.
                    // After making the quiz objects it will be added to the quiz list.
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String code = Objects.requireNonNull(document.get("ID")).toString();
                        String name = "test";
                        String quizID = document.getId();
                        Quiz quiz = new Quiz(code, name, quizID);
                        finalQuizList.add(quiz);
                    }

                    // Update the quiz list with the new information
                    ListView quizListView = findViewById(R.id.Quizzes_list);
                    QuizAdapter adapter = new QuizAdapter(MyQuiz.this, R.layout.myquizzes_line, finalQuizList);
                    quizListView.setAdapter(adapter);
                }
            }
        });

        // This will check if the user created any quizzes (OBS, this is redundant as onComplete will run after this code)
        // TODO: Re-arrange code to run this part within the onComplete method
        quizList = finalQuizList;

        if (quizList.size() < 1) {
            quizList = new ArrayList<>();
            quizList.add(new Quiz("No quizzes", "", ""));
        }

        return quizList;


    }

}
