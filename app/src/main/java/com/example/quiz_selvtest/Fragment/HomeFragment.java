package com.example.quiz_selvtest.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quiz_selvtest.Quiz.CreateQuizAct;
import com.example.quiz_selvtest.Quiz.Quiz;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefragment,container,false);
        ImageView createQuiz = view.findViewById(R.id.createQuizICON);
        EditText joinQuizURL = view.findViewById(R.id.joinAGame);
        EditText joinCode = view.findViewById(R.id.txt_joincode);
        Button joinBtn = view.findViewById(R.id.btn_join_quiz);

        createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateQuizAct.class);
                startActivity(intent);
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = joinCode.getText().toString();
                joinQuiz(pw);
            }
        });
        return view;
    }

    public void joinQuiz(String pw) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("Quizzes");
        Query query = col.whereEqualTo("ID", pw);
        final Activity act = getActivity();
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

    /*public void createQuiz(View view){

        startActivity(new Intent(HomeFragment.this, CreateQuizAct.class));

    }*/
}
