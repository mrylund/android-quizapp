package com.example.quiz_selvtest.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateQuizAct extends AppCompatActivity {
    private String sheet;
    private static final String TAG = "CreateQuizAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
    }

    public void createQuiz(View v) {

        EditText urlET = findViewById(R.id.enterUrl);
        String url = urlET.getText().toString();
        url = url.replace("https://","").replace("http://","");

        if (!url.startsWith("docs.google.com/spreadsheets/d/")) {
            Toast.makeText(CreateQuizAct.this, "This is not a google spreadsheet!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String sheet = getSheetID(url);

        new createQuiz(sheet).execute();
    }

    public class createQuiz extends AsyncTask<String, Integer, String> {
        private String code;

        createQuiz(String newSheet) {
            sheet = newSheet;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                if (!validateSheet(sheet)) return "Fail";
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

            code = generateQuizCode();
            isQuizCodeUnique(code);

            return "Success";
        }

        @Override
        protected void onPostExecute(String s) {

            if (s.equals("Success")) {
                Toast.makeText(CreateQuizAct.this, "Your Quiz code is: " + code,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addSheetToDatabase(String uID, String sheet, String code) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> quiz = new HashMap<>();
        quiz.put("Course", null);
        quiz.put("Creator", uID);
        quiz.put("ID", code);
        quiz.put("Name", null);
        quiz.put("Sheet", sheet);

        db.collection("Quizzes")
                .add(quiz).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "Quiz added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding quiz", e);
                Toast.makeText(CreateQuizAct.this, "Error adding quiz to database, please try again later.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void isQuizCodeUnique(String code) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("Quizzes");
        Query query = col.whereEqualTo("ID", code);
        final Activity act = this;
        query.limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = Objects.requireNonNull(task.getResult()).getDocuments();
                    if (documents.size() < 1 || documents.get(0).getMetadata().hasPendingWrites()) {
                        String uID = FirebaseAuth.getInstance().getUid();
                        addSheetToDatabase(uID, sheet, code);
                    } else {
                        String code = generateQuizCode();
                        isQuizCodeUnique(code);
                    }
                }
            }
        });

    }

    private String generateQuizCode() {
        final String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int charNum = (int)(Math.random()*possibleChars.length());
            code.append(possibleChars.charAt(charNum));
        }

        return code.toString();
    }

    private boolean validateSheet(String sheet) throws IOException, CsvValidationException {
        InputStream input = new URL("https://docs.google.com/spreadsheets/d/" + sheet + "/export?format=csv&id=" + sheet).openStream();
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(input, StandardCharsets.UTF_8)).withSkipLines(8).build();

        List<Question> tempQuestions = new ArrayList<>();
        String[] line;
        while ((line = reader.readNext()) != null) {
            if (line[1].equals("")) break;
            String question = line[1];
            List<String> answers = new ArrayList<>();

            for (int i = 2; i <= 5; i++) {
                if (line[i] != null) {
                    answers.add(line[i]);
                }
            }

            if (answers.size() < 2) {
                Toast.makeText(CreateQuizAct.this, "Question " + (tempQuestions.size() + 1) + " does not have at least 2 answers!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            int tid;
            if (line[6].equals("")) {
                Toast.makeText(CreateQuizAct.this, "Question " + (tempQuestions.size() + 1) + " does not have a time limit set!",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else {
                tid = Integer.parseInt(line[6]);
            }

            String[] correctString = line[7].split(",");
            int[] correct = new int[correctString.length];
            for(int i = 0; i < correctString.length; i++) {
                correct[i] =  Integer.parseInt(correctString[i]);
            }

            if (correct.length < 1) {
                Toast.makeText(CreateQuizAct.this, "Question " + (tempQuestions.size() + 1) + " does not have any correct answer!",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            Question q = new Question(question, answers, correct);

            tempQuestions.add(q);
        }
        if (tempQuestions.size() < 2) {
            Toast.makeText(CreateQuizAct.this, "You must have at least 2 questions to create a quiz!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String getSheetID(String url) {
        url = url.substring(url.indexOf("/d/")+3);
        url = url.substring(0, url.indexOf("/"));
        url = url.trim();
        return url;
    }
}
