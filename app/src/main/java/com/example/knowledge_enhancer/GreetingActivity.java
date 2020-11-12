package com.example.knowledge_enhancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class GreetingActivity extends AppCompatActivity {
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler delayBeforeChangeScreen = new Handler();
                delayBeforeChangeScreen.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(GreetingActivity.this, GreetingActivity2.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 500);

                // disable nút để tránh trường hợp ng dùng click liên tục vào nút
                v.setClickable(false);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setClickable(true);
                    }
                }, 500);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Handler delayBeforeChangeScreen = new Handler();
        delayBeforeChangeScreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GreetingActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 500);

        finish();
    }
}