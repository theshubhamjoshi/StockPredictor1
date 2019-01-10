package com.example.ssj_recognized.stockpredictor;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        start = (Button) findViewById(R.id.start);
        final ImageView imageView = (ImageView) findViewById(R.id.building);
        final Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(2000);
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.startAnimation(animation);
                imageView.setVisibility(View.VISIBLE);
            }
        },1000);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
