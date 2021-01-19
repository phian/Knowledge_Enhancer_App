package com.example.knowledge_enhancer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GamePlay extends AppCompatActivity {
    private int pressCounter;
    private int maxPressCounter;
    private char[] wordCharacters;
    private String answer;
    private int questionCount, totalQuestion; // 2 biến đếm cho số lượng câu hỏi và câu thứ n mà ng chơi đang ở
    private String selectedTopic;
    private int remainingTurnCount;
    private int totalScore;
    private String[] topics;
    private List<Integer> skippedIndex; // Lưu lại các thứ tự câu hỏi ng chơi đã skip

    private TextView currentQuestionCount, score, remainingTurn, wordTopicTitle, wordDescription;
    private ImageView correctAndWrongIcon;
    private ImageButton replayButton;
    private EditText resultET;
    private LinearLayout characterLay, characterLay1, characterLay2;
    private FrameLayout gameBackgroundLay;
    ImageView gamePlayBackgroundImg;

    // Các biến để thao tác với database
    DatabaseHelper databaseHelper;
    private List<Quiz> quizzes;
    Topic currentTopic;
    int currentHighScore;


    /**
     * Những việc cần làm:
     * 1. Hiển thị random hình background
     * 2. Quay lại các câu đã skip
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        databaseHelper = new DatabaseHelper(GamePlay.this);
        getQuizList();

        initGameVariables();
        initGamePlayControls();
        initGamePlayBackground();

        for (int i = 0; i < wordCharacters.length; i++) {
            if (i < 5) {
                addView(((LinearLayout) findViewById(R.id.characters_lay)), wordCharacters[i], resultET);
            } else if (i >= 5 && i < 10) {
                addView(((LinearLayout) findViewById(R.id.characters_lay1)), wordCharacters[i], resultET);
            } else {
                addView(((LinearLayout) findViewById(R.id.characters_lay2)), wordCharacters[i], resultET);
            }
        }
    }

    // Hàm nhận topic dc pass từ screen trc tới
    private void getTopic() {
        Bundle topicIndex = getIntent().getExtras();
        if (topicIndex != null) {
            selectedTopic = topicIndex.getString("SelectedTopic");
//            Toast.makeText(GamePlay.this, selectedTopic, Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm get các câu hỏi tương ứng với chủ đề đã chọn
    private void getQuizList() {
        try {
            getTopic();
            quizzes = databaseHelper.getAllQuizByTopicID((Integer.parseInt(selectedTopic) + 1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Hàm để đọc high score hiện tại từ database
    private void getTopicHighScore() {
        try {
            currentTopic = databaseHelper.getTopicByID(Integer.parseInt(selectedTopic + 1));
            currentHighScore = currentTopic.getTopicStar();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Hàm add các box hiển thị từng ký tự trong từ đang chơi
    private void addView(LinearLayout characterLay, char character, EditText result) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        layoutParams.rightMargin = 20; // Margin cho các ký tự trong phần chọn

        // Character box
        final TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.text_color));
        textView.setGravity(Gravity.CENTER);
        textView.setText(String.valueOf(character).toUpperCase());
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);


        Typeface fontStyle = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        currentQuestionCount.setTypeface(fontStyle);
        score.setTypeface(fontStyle);
        remainingTurn.setTypeface(fontStyle);
        wordTopicTitle.setTypeface(fontStyle);
        wordDescription.setTypeface(fontStyle);

        result.setTypeface(fontStyle);
        textView.setTypeface(fontStyle);

        textView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (pressCounter < maxPressCounter) {
                    if (pressCounter == 0)
                        result.setText("");

                    result.setText((result.getText().toString() + character).toUpperCase());
//                        textView.startAnimation(bigsmallforth);
                    textView.setEnabled(false);
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
    private char[] shuffleArray(char[] wordCharacters) {
        Random randCharacter = new Random();

        // Vòng lặp random kí tự từ vị trí thì 0 đén i
        for (int i = wordCharacters.length - 1; i > 0; i--) {
            int characterIndex = randCharacter.nextInt(i + 1);
            char chara = wordCharacters[characterIndex]; // Lưu kí tự đã random dc
            // Chuyển ký tự đã random ra cuối để ko random ra nữa
            wordCharacters[characterIndex] = wordCharacters[i];
            wordCharacters[i] = chara;
        }

        return wordCharacters;
    }

    // Hàm reset các giá trị về ban đầu khi ng dùng hết lượt chơi
    private void doValidate() {
        pressCounter = 0;


//        EditText resultET = (EditText) findViewById(R.id.resultET);
//        characterLay = (LinearLayout) findViewById(R.id.characters_lay);

        // Nếu trl đúng
        if (resultET.getText().toString().toLowerCase().equals(answer.toLowerCase())) {
            resultET.setText("");
            updateHighScore();

            correctAndWrongIcon.setImageResource(R.drawable.check);
            displayIcon();

            if (questionCount < totalQuestion) {
                questionCount++;
                currentQuestionCount.setText("Question: " + questionCount + "/" + totalQuestion);
            }

            // Upgrade score
            score.setText("Score: " + (questionCount - 1));

            // Move to next word
            answer = quizzes.get(questionCount - 1).getQuizAnswer();

            remainingTurnCount = answer.length();
            maxPressCounter = answer.replaceAll("\\W","").toCharArray().length;

            wordDescription.setText(quizzes.get(questionCount - 1).getQuizQuestion());
            wordCharacters = new char[answer.replaceAll("\\W","").toCharArray().length + 1];
            char[] temp = answer.replaceAll("\\W","").toCharArray();
            for (int i = 0; i < wordCharacters.length - 1; i++) {
                wordCharacters[i] = temp[i];
            }

            // Random ký tự đánh lừa để add vào array
            Random r = new Random();
            wordCharacters[wordCharacters.length - 1] = (char)(r.nextInt(26) + 'a');
            wordCharacters = shuffleArray(wordCharacters);

            // Đổi background
            initGamePlayBackground();
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
        characterLay1.removeAllViews();
        characterLay2.removeAllViews();

        for (int i = 0; i < wordCharacters.length; i++) {
            if (i < 5) {
                addView(characterLay, wordCharacters[i], resultET);
            } else if (i >= 5 && i < 10) {
                addView(characterLay1, wordCharacters[i], resultET);
            } else  {
                addView(characterLay2, wordCharacters[i], resultET);
            }
        }

        // Score
        score.setText("Score: " + (questionCount - 1));
    }

    // Hàm khởi tạo các giá trị ban đầu cho các biến
    private void initGameVariables() {
        answer = quizzes.get(0).getQuizAnswer();
        wordCharacters = new char[answer.replaceAll("\\W","").toCharArray().length + 1];
        char[] temp = answer.replaceAll("\\W","").toCharArray();
        for (int i = 0; i < wordCharacters.length - 1; i++) {
            wordCharacters[i] = temp[i];
        }

        // Random ký tự đánh lừa để add vào array
        Random r = new Random();
        wordCharacters[wordCharacters.length - 1] = (char)(r.nextInt(26) + 'a');
        wordCharacters = shuffleArray(wordCharacters);

        pressCounter = 0;
        maxPressCounter = answer.replaceAll("\\W","").toCharArray().length; /*Lấy số lần sau khi đã bỏ khoảng trắng*/

        questionCount = 1;
        totalQuestion = quizzes.size();
        remainingTurnCount = answer.replaceAll("\\W","").toCharArray().length;
        totalScore = 0;
        topics = new String[]{"Jobs", "Sports", "Foods and Drinks", "Animals", "Cloths", "Cities & Countries", "Family", "School", "Languages", "Geographic Terminology",
                "House Ware & Kitchen", "House & Garden", "Things and Materials", "Travel", "Transport", "Music", "Human Body", "Pharmacy", "Health & Diseases",
                "Festival", "Shopping"};
        skippedIndex = new ArrayList<Integer>();
    }

    // Hàm khởi tạo state ban đầu cho các controls
    private void initGamePlayControls() {
        currentQuestionCount = (TextView) findViewById(R.id.current_question_text);
        score = (TextView) findViewById(R.id.score_text);
        remainingTurn = (TextView) findViewById(R.id.remainingTurn);
        wordTopicTitle = (TextView) findViewById(R.id.topic_title);
        wordDescription = (TextView) findViewById(R.id.word_description_text);
        correctAndWrongIcon = (ImageView) findViewById(R.id.correctAndWrongIcon);
        replayButton = (ImageButton) findViewById(R.id.replay_button);
        resultET = (EditText) findViewById(R.id.resultET);
        characterLay = (LinearLayout) findViewById(R.id.characters_lay);
        characterLay1 = (LinearLayout) findViewById(R.id.characters_lay1);
        characterLay2 = (LinearLayout) findViewById(R.id.characters_lay2);
        gameBackgroundLay = (FrameLayout) findViewById(R.id.game_background_lay);
        gamePlayBackgroundImg = (ImageView) findViewById(R.id.game_background_img);

        // Ẩn icon đi
        correctAndWrongIcon.animate().alpha(0).setDuration(0).setStartDelay(0);

        currentQuestionCount.setText("Question: " + questionCount + "/" + totalQuestion);
        remainingTurn.setText("Remaining turn: " + remainingTurnCount);
        score.setText("Score: 0");

        wordTopicTitle.setText("Guess the word of " + topics[Integer.parseInt(selectedTopic)] + " topic");
        wordDescription.setText(quizzes.get(0).getQuizQuestion());

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
                    wordDescription.setText(quizzes.get(questionCount - 1).getQuizQuestion());

                    // Add lại kí vào ô chọn và add vào layout
                    characterLay.removeAllViews();
                    characterLay1.removeAllViews();
                    characterLay2.removeAllViews();
                    for (int i = 0; i < wordCharacters.length; i++) {
                        if (i < 5) {
                            addView(characterLay, wordCharacters[i], resultET);
                        } else if (i >= 5 && i < 10) {
                            addView(characterLay1, wordCharacters[i], resultET);
                        } else {
                            addView(characterLay2, wordCharacters[i], resultET);
                        }
                    }

                    // Reset số lượt còn lại
                    remainingTurnCount = answer.length() - 1;
                    remainingTurn.setText("Remaining turn: " + remainingTurnCount);

                    // Reset lại biến đếm số lượt
                    pressCounter = 0;
                }
            }
        });
    }

    private void initGamePlayBackground() {
        int randResult = (int)(Math.random() * ((5 - 0) + 1));

        switch (randResult) {
            case 0:
                gamePlayBackgroundImg.setImageResource(R.drawable.gameplay_background1);
                break;
            case 1:
                gamePlayBackgroundImg.setImageResource(R.drawable.gameplay_background2);
                break;
            case 2:
                gamePlayBackgroundImg.setImageResource(R.drawable.gameplay_background3);
                break;
            case 3:
                gamePlayBackgroundImg.setImageResource(R.drawable.gameplay_background4);
                break;
            case 4:
                gamePlayBackgroundImg.setImageResource(R.drawable.gameplay_background5);
                break;
        }
    }

    // Hảm kiểm tra highscore để cập nhật
    private void updateHighScore() {
        getTopicHighScore();
        switch (currentHighScore) {
            case 0:
                if (questionCount > 1) {
                    databaseHelper.updateStarTopic(1, Integer.parseInt(selectedTopic + 1));
                }
                break;
            case 1:
                if (questionCount > 2) {
                    databaseHelper.updateStarTopic(2, Integer.parseInt(selectedTopic + 1));
                }
            case 2:
                if (questionCount > 3) {
                    databaseHelper.updateStarTopic(3, Integer.parseInt(selectedTopic + 1));
                }
                break;
            case 3:
                if (questionCount > 4) {
                    databaseHelper.updateStarTopic(4, Integer.parseInt(selectedTopic + 1));
                }
                break;
            default:
                if (questionCount > 5) {
                    databaseHelper.updateStarTopic(5, Integer.parseInt(selectedTopic + 1));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_OK);
    }
}