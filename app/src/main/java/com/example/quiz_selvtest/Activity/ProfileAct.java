package com.example.quiz_selvtest.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.quiz_selvtest.Fragment.FragmentController;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DocumentReference userInfo = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        userInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        TextView userName = findViewById(R.id.textView4);
                        userName.setText((String)document.get("Username"));
                    }
                }
            }
        });


        findViewById(R.id.profile_toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAct.this, FragmentController.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ProfileAct.this, FragmentController.class);
        startActivity(intent);
        finish();
    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsAct.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, StartScreenAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
