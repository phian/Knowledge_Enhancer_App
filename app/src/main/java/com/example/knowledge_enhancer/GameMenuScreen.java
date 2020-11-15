package com.example.knowledge_enhancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class GameMenuScreen extends AppCompatActivity {
    ViewPager vocabMenuVP;
    VocabMenuAdapter vocabMenuAdapter;
    List<VocabMenuItem> items;
    Integer[] backgroundColors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu_screen);

        initFirstStateForVariables();
    }

    private void initFirstStateForVariables() {
        items = new ArrayList<>();
        items.add(new VocabMenuItem(R.drawable.jobs, "Jobs"));
        items.add(new VocabMenuItem(R.drawable.sports, "Sports"));
        items.add(new VocabMenuItem(R.drawable.foods, "Foods"));
        items.add(new VocabMenuItem(R.drawable.animals, "Animals"));
        items.add(new VocabMenuItem(R.drawable.cloths, "Cloths"));
        items.add(new VocabMenuItem(R.drawable.countries, "Countries"));

        vocabMenuAdapter = new VocabMenuAdapter(items, this);

        vocabMenuVP = (ViewPager) findViewById(R.id.vp_vocab_menu);
        vocabMenuVP.setAdapter(vocabMenuAdapter);
        vocabMenuVP.setPadding(100, 0, 100, 0);
        vocabMenuVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < vocabMenuAdapter.getCount() - 1 && position < backgroundColors.length - 1) {
                    vocabMenuVP.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(positionOffset,
                                    backgroundColors[position],
                                    backgroundColors[position + 1]
                            ));
                } else {
                    vocabMenuVP.setBackgroundColor(backgroundColors[backgroundColors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Integer[] colors_temp = {
                getResources().getColor(R.color.job_background_color),
                getResources().getColor(R.color.sport_background_color),
                getResources().getColor(R.color.food_background_color),
                getResources().getColor(R.color.animal_color),
                getResources().getColor(R.color.cloth_background_color),
                getResources().getColor(R.color.country_background_color),
        };

        backgroundColors = colors_temp;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Handler delayBeforeChangeScreen = new Handler();
        delayBeforeChangeScreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameMenuScreen.this, GreetingActivity2.class);
                finish();
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 500);
    }
}