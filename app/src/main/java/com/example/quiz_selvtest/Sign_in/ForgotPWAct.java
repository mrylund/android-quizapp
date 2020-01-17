package com.example.quiz_selvtest.Sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.quiz_selvtest.Activity.StartScreenAct;
import com.example.quiz_selvtest.R;

public class ForgotPWAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

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
            public void onClick(View v) {
                animationView.playAnimation();
                }

        });
    }
}