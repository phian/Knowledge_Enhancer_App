package com.example.knowledge_enhancer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlay extends AppCompatActivity {
    private int pressCounter;
    private int maxPressCounter;
    private String[] wordCharacters = {"R", "I", "B", "D", "X"};
    private String answer;
    private int questionCount, totalQuestion; // 2 biến đếm cho số lượng câu hỏi và câu thứ n mà ng chơi đang ở
    private String selectedTopic;
    private int remainingTurnCount;
    private int totalScore;
    private String[] topics;
    private List<Integer> skippedIndex; // Lưu lại các thứ tự câu hỏi ng chơi đã skip

    private TextView currentQuestionCount, score, remainingTurn, wordTopicTitle, wordDescription;
    private MaterialButton skipButton;
    private ImageView correctAndWrongIcon;
    private ImageButton replayButton;
    private EditText resultET;
    private LinearLayout characterLay;
    private LinearLayout gameBackgroundLay;


    /**
     * Những việc cần làm:
     * 1. Hiển thị random hình background
     * 2. Quay lại các câu đã skip
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        getTopic();

        initGameVariables();
        initGamePlayControls();

        for (String character : wordCharacters) {
            addView(((LinearLayout) findViewById(R.id.characters_lay)), character, resultET);
        }
    }

    // Hàm nhận topic dc pass từ screen trc tới
    private void getTopic() {
        Bundle topicIndex = getIntent().getExtras();
        if (topicIndex != null) {
            selectedTopic = topicIndex.getString("SelectedTopic");
        }
    }

    // Hàm add các box hiển thị từng ký tự trong từ đang chơi
    private void addView(LinearLayout characterLay, String character, EditText result) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        layoutParams.rightMargin = 20; // Margin cho các ký tự trong phần chọn

        // Character box
        final TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.text_color));
        textView.setGravity(Gravity.CENTER);
        textView.setText(character);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface fontStyle = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        currentQuestionCount.setTypeface(fontStyle);
        score.setTypeface(fontStyle);
        remainingTurn.setTypeface(fontStyle);
        wordTopicTitle.setTypeface(fontStyle);
        wordDescription.setTypeface(fontStyle);
        skipButton.setTypeface(fontStyle);

        result.setTypeface(fontStyle);
        textView.setTypeface(fontStyle);

        textView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (pressCounter < maxPressCounter) {
                    if (pressCounter == 0)
                        result.setText("");

                    result.setText(result.getText().toString() + character);
//                        textView.startAnimation(bigsmallforth);
                    textView.animate().alpha(0).setDuration(300);
                    pressCounter++;

                    // Nếu ấn thì sẽ mất bớt lượt ấn
                    remainingTurnCount--;
                    remainingTurn.setText("Remaining turn: " + remainingTurnCount);

                    if (pressCounter == maxPressCounter)
                        doValidate();

                }
            }
        });

        characterLay.addView(textView);
    }

    // Hàm đọc random ra các charater từ mảng các chữ cái có trong từ
    private String[] shuffleArray(String[] wordCharacters) {
        Random randCharacter = new Random();

        // Vòng lặp random kí tự từ vị trí thì 0 đén i
        for (int i = wordCharacters.length - 1; i > 0; i--) {
            int characterIndex = randCharacter.nextInt(i + 1);
            String chara = wordCharacters[characterIndex]; // Lưu kí tự đã random dc
            // Chuyển ký tự đã random ra cuối để ko random ra nữa
            wordCharacters[characterIndex] = wordCharacters[i];
            wordCharacters[i] = chara;
        }

        return wordCharacters;
    }

    // Hàm reset các giá trị về ban đầu khi ng dùng hết lượt chơi
    private void doValidate() {
        pressCounter = 0;
        remainingTurnCount = answer.length();

//        EditText resultET = (EditText) findViewById(R.id.resultET);
//        characterLay = (LinearLayout) findViewById(R.id.characters_lay);

        // Nếu trl đúng
        if (resultET.getText().toString().equals(answer)) {
            resultET.setText("");

            correctAndWrongIcon.setImageResource(R.drawable.check);
            displayIcon();

            if (questionCount < totalQuestion) {
                questionCount++;
                currentQuestionCount.setText("Question: " + questionCount + "/" + totalQuestion);
            }

            // Nếu ng chơi trả lời đúng câu đã skip trước đó thì xoá giá trị đó đi
//            if (skippedIndex.contains(questionCount - 1)) {
//                skippedIndex.remove((questionCount - 1));
//            }

            // Upgrade score
            score.setText("Score: " + (questionCount - 1));

            // Move to next word
        } else { // Nếu ng dùng trả lời sai
            resultET.setText("");

            remainingTurnCount = answer.length();
            remainingTurn.setText("Remaining turn: " + remainingTurnCount);

            correctAndWrongIcon.setImageResource(R.drawable.wrong);
            displayIcon();
        }

        // Reset remaining turns
        remainingTurn.setText("Remaining turn: " + remainingTurnCount);

        wordCharacters = shuffleArray(wordCharacters);
        characterLay.removeAllViews();

        for (String character : wordCharacters) {
            addView(characterLay, character, resultET);
        }

        // Score
        score.setText("Score: " + (questionCount - 1));
    }

    // Hàm khởi tạo các giá trị ban đầu cho các biến
    private void initGameVariables() {
        pressCounter = 0;
        maxPressCounter = 4;
        answer = "BIRD";
        wordCharacters = shuffleArray(wordCharacters);
        questionCount = 1;
        totalQuestion = 10;
        remainingTurnCount = answer.length();
        totalScore = 0;
        topics = new String[]{"Jobs", "Sports", "Foods", "Animals", "Cloths", "Cities"};
        skippedIndex = new ArrayList<Integer>();
    }

    // Hàm khởi tạo state ban đầu cho các controls
    private void initGamePlayControls() {
        currentQuestionCount = (TextView) findViewById(R.id.current_question_text);
        score = (TextView) findViewById(R.id.score_text);
        remainingTurn = (TextView) findViewById(R.id.remainingTurn);
        wordTopicTitle = (TextView) findViewById(R.id.topic_title);
        wordDescription = (TextView) findViewById(R.id.word_description_text);
        skipButton = (MaterialButton) findViewById(R.id.skip_button);
        correctAndWrongIcon = (ImageView) findViewById(R.id.correctAndWrongIcon);
        replayButton = (ImageButton) findViewById(R.id.replay_button);
        resultET = (EditText) findViewById(R.id.resultET);
        characterLay = (LinearLayout) findViewById(R.id.characters_lay);
        gameBackgroundLay = (LinearLayout) findViewById(R.id.game_background_lay);

        // Ẩn icon đi
        correctAndWrongIcon.animate().alpha(0).setDuration(0).setStartDelay(0);

        currentQuestionCount.setText("Question: " + questionCount + "/" + totalQuestion);
        remainingTurn.setText("Remaining turn: " + remainingTurnCount);
        score.setText("Score: 0");

        wordTopicTitle.setText("Guess the topic of " + topics[Integer.parseInt(selectedTopic)] + " topic");

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionCount == totalQuestion) {
                    // Quay lại những câu chưa hoàn thành (nếu có)


                    return;
                }

                questionCount++;
                currentQuestionCount.setText("Question: " + questionCount + "/" + totalQuestion);

                skippedIndex.add(questionCount);
            }
        });

        onReplayButtonClickListener();;
    }

    // Hàm để show animation khi người chơi hoàn thành lượt chơi
    private void displayIcon() {
        correctAndWrongIcon.animate().alpha(1).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                correctAndWrongIcon.animate().alpha(0).setStartDelay(300).setDuration(300);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    // Hàm cho nút replay
    private void onReplayButtonClickListener() {
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!resultET.getText().toString().isEmpty()) // Nếu ng chơi đã có chọn từ rồi thì mới reset lại
                {
                    resultET.setText(""); // Reset lại ô edit text chứa kq trả lời

                    wordCharacters = shuffleArray(wordCharacters); // random lại các kí tự

                    // Add lại kí vào ô chọn và add vào layout
                    characterLay.removeAllViews();
                    for (String character : wordCharacters) {
                        addView(characterLay, character, resultET);
                    }

                    // Reset số lượt còn lại
                    remainingTurnCount = answer.length();
                    remainingTurn.setText("Remaining turn: " + remainingTurnCount);

                    // Reset lại biến đếm số lượt
                    pressCounter = 0;
                }
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
                Intent intent = new Intent(GamePlay.this, GreetingActivity2.class);
                finish();
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 500);
    }
}