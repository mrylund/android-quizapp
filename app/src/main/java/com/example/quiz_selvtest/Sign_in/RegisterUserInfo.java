package com.example.quiz_selvtest.Sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.Quiz.CreateQuizAct;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserInfo extends AppCompatActivity {
    private static final String TAG = "RegisterUserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_info);
    }


    public void registerInfo(View v) {
        EditText usernameET = findViewById(R.id.registerUsername);
        EditText languageET = findViewById(R.id.registerLanguage);
        String username = usernameET.getText().toString();
        String language = languageET.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Username", username);
        userInfo.put("Language", language);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        db.collection("Users").document(currentUser.getUid())
                .set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "User info added)");

                Intent intent = new Intent(RegisterUserInfo.this, FragmentController.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
    }
}
