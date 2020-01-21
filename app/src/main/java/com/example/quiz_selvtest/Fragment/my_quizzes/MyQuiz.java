package com.example.quiz_selvtest.Fragment.my_quizzes;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class MyQuiz extends Fragment {
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myquizzes_fragment,container,false);
        ListView quizListView = view.findViewById(R.id.Quizzes_list);


        ArrayList<Quiz> quizzes = getUserQuizzes();

        QuizAdapter adapter = new QuizAdapter(getActivity(), R.layout.myquizzes_line, quizzes);
        quizListView.setAdapter(adapter);


        return view;
    }


    //Inspireret af : https://stackoverflow.com/a/53606612
    protected ArrayList<Quiz> getUserQuizzes() {
        CountDownLatch done = new CountDownLatch(1);

        ArrayList<Quiz> quizList = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("Quizzes");
        Query query = col.whereEqualTo("Creator", mAuth.getCurrentUser().getUid());
        ArrayList<Quiz> finalQuizList = quizList;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String code = Objects.requireNonNull(document.get("ID")).toString();
                        String name = "test";
                        String quizID = document.getId();
                        Quiz quiz = new Quiz(code, name, quizID);
                        finalQuizList.add(quiz);
                    }
                    ListView quizListView = view.findViewById(R.id.Quizzes_list);
                    QuizAdapter adapter = new QuizAdapter(getActivity(), R.layout.myquizzes_line, finalQuizList);
                    quizListView.setAdapter(adapter);
                }
            }
        });

        quizList = finalQuizList;

        if (quizList.size() < 1) {
            quizList = new ArrayList<>();
            quizList.add(new Quiz("No quizzes", "", ""));
        }

        return quizList;


    }

}
