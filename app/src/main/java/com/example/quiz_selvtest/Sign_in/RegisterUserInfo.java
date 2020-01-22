package com.example.quiz_selvtest.Sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        EditText usernameET = findViewById(R.id.txtUsername);
        EditText languageET = findViewById(R.id.txtLanguage);
        String username = usernameET.getText().toString();
        String language = languageET.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Make a map containing the info the user entered
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("Username", username);
        userInfo.put("Language", language);

        // Get the current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Add the userdata to the database, then redirect them to the home
        assert currentUser != null;
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
