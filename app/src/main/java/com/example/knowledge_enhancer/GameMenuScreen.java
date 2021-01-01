package com.example.knowledge_enhancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class GameMenuScreen extends AppCompatActivity {
    private ViewPager vocabMenuVP;
    private VocabMenuAdapter vocabMenuAdapter;
    private List<VocabMenuItem> items;
    private Integer[] backgroundColors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private MaterialButton playButton;

    private int selectedTopicIndex; // Lưu lại topic mà ng dùng chọn

    private List<Integer> highScoreList;
    private ImageView starHighScoreImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu_screen);

        initHighScoreList();
        initFirstStateForVariables();
    }

    private void initFirstStateForVariables() {
        initMenuItems();

        initMenuVP();

        //------------------------------ Play game button-----------------------------------------//
        initPlayButton();
    }

    private void initHighScoreList() {
        highScoreList = new ArrayList<>();

//        highScoreList.add(1);
        for(int i = 0; i < 21; i++) {
            highScoreList.add(0);
        }

        initHighScoreStarImg(highScoreList.get(0));
    }

    private void initHighScoreStarImg(int stars) {
        starHighScoreImg = (ImageView) findViewById(R.id.high_score_star_img);
        switch (stars) {
            case 0:
                starHighScoreImg.setImageResource(R.drawable.star_0);
                break;
            case 1:
                starHighScoreImg.setImageResource(R.drawable.star_1);
                break;
            case 2:
                starHighScoreImg.setImageResource(R.drawable.star_2);
                break;
            case 3:
                starHighScoreImg.setImageResource(R.drawable.star_3);
                break;
            case 4:
                starHighScoreImg.setImageResource(R.drawable.star_4);
                break;
            case 5:
                starHighScoreImg.setImageResource(R.drawable.star_5);
                break;
        }
    }

    private void initMenuItems() {
        items = new ArrayList<>();
        items.add(new VocabMenuItem(R.drawable.jobs, "Jobs"));
        items.add(new VocabMenuItem(R.drawable.sports, "Sports"));
        items.add(new VocabMenuItem(R.drawable.foods, "Foods & Drinks"));
        items.add(new VocabMenuItem(R.drawable.animals, "Animals"));
        items.add(new VocabMenuItem(R.drawable.cloths, "Cloths"));
        items.add(new VocabMenuItem(R.drawable.countries, "Cities & Countries"));
        items.add(new VocabMenuItem(R.drawable.family, "Family"));
        items.add(new VocabMenuItem(R.drawable.school, "School"));
        items.add(new VocabMenuItem(R.drawable.languages, "Languages"));
        items.add(new VocabMenuItem(R.drawable.geographic_terminology, "Geographic Terminology"));
        items.add(new VocabMenuItem(R.drawable.houseware_and_kitchen, "House Ware & Kitchen"));
        items.add(new VocabMenuItem(R.drawable.house_and_garden, "House & Garden"));
        items.add(new VocabMenuItem(R.drawable.material, "Things and Materials"));
        items.add(new VocabMenuItem(R.drawable.travel, "Travel"));
        items.add(new VocabMenuItem(R.drawable.transport, "Transport"));
        items.add(new VocabMenuItem(R.drawable.music, "Music"));
        items.add(new VocabMenuItem(R.drawable.human_body, "Human Body"));
        items.add(new VocabMenuItem(R.drawable.pharmacy, "Pharmacy"));
        items.add(new VocabMenuItem(R.drawable.health_and_disease, "Health & Diseases"));
        items.add(new VocabMenuItem(R.drawable.festival, "Festival"));
        items.add(new VocabMenuItem(R.drawable.shopping, "Shopping"));

        vocabMenuAdapter = new VocabMenuAdapter(items, this);
    }

    private Integer[] menuBackgroundColors() {
        Integer[] colors_temp = {
                getResources().getColor(R.color.job_background_color),
                getResources().getColor(R.color.sport_background_color),
                getResources().getColor(R.color.food_background_color),
                getResources().getColor(R.color.animal_color),
                getResources().getColor(R.color.cloth_background_color),
                getResources().getColor(R.color.country_background_color),
                getResources().getColor(R.color.family_background_color),
                getResources().getColor(R.color.school_background_color),
                getResources().getColor(R.color.languages_background_color),
                getResources().getColor(R.color.geographic_terminology_background_color),
                getResources().getColor(R.color.houseware_and_kitchen_background_color),
                getResources().getColor(R.color.house_and_garden_background_color),
                getResources().getColor(R.color.things_and_material_background_color),
                getResources().getColor(R.color.travel_background_color),
                getResources().getColor(R.color.transport_background_color),
                getResources().getColor(R.color.music_background_color),
                getResources().getColor(R.color.human_body_background_color),
                getResources().getColor(R.color.pharmacy_background_color),
                getResources().getColor(R.color.health_and_disease_background_color),
                getResources().getColor(R.color.festival_background_color),
                getResources().getColor(R.color.shopping_background_color),
        };

        return colors_temp;
    }

    private void initMenuVP() {
        selectedTopicIndex = 0;

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

                    selectedTopicIndex = position;
                    initHighScoreStarImg(highScoreList.get(selectedTopicIndex));
                } else {
                    vocabMenuVP.setBackgroundColor(backgroundColors[backgroundColors.length - 1]);

                    selectedTopicIndex = backgroundColors.length - 1;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        backgroundColors = menuBackgroundColors();
    }

    private void initPlayButton() {
        playButton = (MaterialButton) findViewById(R.id.select_vocab_option_btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler delayBeforeChangeScreen = new Handler();
                delayBeforeChangeScreen.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(GameMenuScreen.this, GamePlay.class);
                        intent.putExtra("SelectedTopic", String.valueOf(selectedTopicIndex));
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                }, 500);
            }
        });
    }
}