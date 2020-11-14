package com.example.knowledge_enhancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class GreetingActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Handler delayBeforeChangeScreen = new Handler();
        delayBeforeChangeScreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GreetingActivity2.this, GreetingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Handler delayBeforeChangeScreen = new Handler();
        delayBeforeChangeScreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GreetingActivity2.this, GreetingActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 500);
    }
}