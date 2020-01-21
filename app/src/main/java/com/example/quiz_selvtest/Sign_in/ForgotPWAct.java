package com.example.quiz_selvtest.Sign_in;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.Quiz.CreateQuizAct;
import com.example.quiz_selvtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPWAct extends AppCompatActivity {
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

        ImageView backButton = findViewById(R.id.goBackBTN);
        Button submitButton = findViewById(R.id.submitForgotButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             resetPW();
            }
        });
    }

    public void resetPW() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        EditText emailET = findViewById(R.id.forgotpwMail);
        String emailAddress = emailET.getText().toString();

        if (!emailAddress.matches(emailPattern)) {
            Toast.makeText(ForgotPWAct.this, "This is not a valid email!",
                    Toast.LENGTH_LONG).show();
        }

        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPWAct.this, "Password reset email has been sent to: " + emailAddress,
                            Toast.LENGTH_LONG).show();
                    playAnimation();
                } else {
                    Toast.makeText(ForgotPWAct.this, "Email does not exist!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void playAnimation() {
        //Animation from lottiefiles.com
        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.completeAnimation);
        animationView.setAnimation("519-load-complete.json");

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animationView.setVisibility(View.VISIBLE);
            }

            //When animation is over, clear the animation and finish the activity.
            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animationView.playAnimation();
    }
}
