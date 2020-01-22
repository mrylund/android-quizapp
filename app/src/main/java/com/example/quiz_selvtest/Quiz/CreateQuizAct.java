package com.example.quiz_selvtest.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_selvtest.R;
import com.example.quiz_selvtest.Sign_in.NonStudentSigninAct;
import com.example.quiz_selvtest.Sign_in.RegisterAct;
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

        TextView helpBTN = findViewById(R.id.btnHelp);

        helpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateQuizAct.this, HelpAct.class));
            }
        });

    }

    public void createQuiz(View v) {
        EditText urlET = findViewById(R.id.txtURL);
        String url = urlET.getText().toString();

        // Remove https or http from the link
        url = url.replace("https://","").replace("http://","");

        // Check if the link URL starts like a google spreadsheet
        if (!url.startsWith("docs.google.com/spreadsheets/d/")) {
            Toast.makeText(CreateQuizAct.this, "This is not a google spreadsheet!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the sheetID
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
                // Try to validate the sheet, this checks if all fields are filled in
                if (!validateSheet(sheet)) return "Fail";
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

            // Generate a quiz code and check if it is unique
            code = generateQuizCode();
            isQuizCodeUnique(code);

            return "Success";
        }

        @Override
        protected void onPostExecute(String s) {

            //TODO Make a better way to notify a user with their quiz code, when the quiz code has been generated.
            if (s.equals("Success")) {
                Toast.makeText(CreateQuizAct.this, "Your Quiz code is: " + code,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addSheetToDatabase(String uID, String sheet, String code) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Make a map with all the values that is needed
        // TODO: Insert a course for the quiz, when courses has been implemented
        // TODO: Insert a name for a quiz, could use the sheet name?
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


    private void isQuizCodeUnique(final String code) {

        // Query to check if the quiz code already exists
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("Quizzes");
        Query query = col.whereEqualTo("ID", code);
        final Activity act = this;
        query.limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = Objects.requireNonNull(task.getResult()).getDocuments();
                    // If quiz code is unique, add it to the database, else generate a new code
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
        // Make a random quiz code containing Alphanumeric characters, and make it 6 characters long
        final String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int charNum = (int)(Math.random()*possibleChars.length());
            code.append(possibleChars.charAt(charNum));
        }

        return code.toString();
    }

    private boolean validateSheet(String sheet) throws IOException, CsvValidationException {
        // Get the google sheet as a csv file and make a new CSV reader to read the file
        InputStream input = new URL("https://docs.google.com/spreadsheets/d/" + sheet + "/export?format=csv&id=" + sheet).openStream();
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(input, StandardCharsets.UTF_8)).withSkipLines(8).build();

        List<Question> tempQuestions = new ArrayList<>();
        String[] line;

        // Loop through lines in the CSV file until no lines are left
        while ((line = reader.readNext()) != null) {
            if (line[1].equals("")) break; // If the question field is empty, quit the while loop.
            String question = line[1];
            List<String> answers = new ArrayList<>();

            for (int i = 2; i <= 5; i++) {
                if (line[i] != null) {
                    answers.add(line[i]);
                }
            }

            // Check if the question has at least two answers
            if (answers.size() < 2) {
                showToast("Question " + (tempQuestions.size() + 1) + " does not have at least 2 answers!");
                return false;
            }

            // Check if the time is set
            int tid;
            if (line[6].equals("")) {
                showToast("Question " + (tempQuestions.size() + 1) + " does not have a time limit set!");
                return false;
            } else {
                tid = Integer.parseInt(line[6]);
            }

            // Save all correct answers
            String[] correctString = line[7].split(",");
            int[] correct = new int[correctString.length];
            for(int i = 0; i < correctString.length; i++) {
                correct[i] =  Integer.parseInt(correctString[i]);
            }

            // Check if the question has at least 1 correct answer
            if (correct.length < 1) {
                showToast( "Question " + (tempQuestions.size() + 1) + " does not have any correct answer!");
                return false;
            }

            // Create the question object with the gathered information and add it to the question list
            Question q = new Question(question, answers, correct);
            tempQuestions.add(q);
        }

        // Check if the quiz has at least 2 questions specified
        if (tempQuestions.size() < 2) {
            showToast("You must have at least 2 questions to create a quiz!");
            return false;
        }

        return true;
    }


    // Method found at: https://stackoverflow.com/a/12897386
    public void showToast(final String toast)
    {
        // Run a toast on the UI thread
        runOnUiThread(() -> Toast.makeText(CreateQuizAct.this, toast, Toast.LENGTH_SHORT).show());
    }

    // Get a sheet ID from a full URL
    private String getSheetID(String url) {
        url = url.substring(url.indexOf("/d/")+3);
        url = url.substring(0, url.indexOf("/"));
        url = url.trim();
        return url;
    }
}
