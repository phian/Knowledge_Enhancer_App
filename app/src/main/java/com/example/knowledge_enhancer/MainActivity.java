package com.example.knowledge_enhancer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button startButton, test;
    DatabaseHelper databaseHelper;
    private static final String TAG = "SQLite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "#MainActivity MainActivity.onCreate ... ");
        databaseHelper = new DatabaseHelper((MainActivity.this));
        databaseHelper.getAllTopic();
        Log.i(TAG, "#MainActivity databaseHelper.getAllTopic() ... ");
        // Để tạm
        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler delayBeforeChangeScreen = new Handler();
                delayBeforeChangeScreen.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, GameMenuScreen.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}