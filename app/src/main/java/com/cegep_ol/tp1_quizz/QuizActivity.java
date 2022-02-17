package com.cegep_ol.tp1_quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prefs;

    private TextView tvUsername;
    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvHighscore;

    private Button btnPrevious;
    private Button btnNext;
    private Button btnTrue;
    private Button btnFalse;
    private Button btnShare;
    private Button btnConfigure;

    private ArrayList<String> affirmations;
    private ArrayList<Boolean> answers;

    private Integer currentQuestion;
    private Boolean correctAnswer;
    private Integer score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        prefs = getSharedPreferences("QuizzSave", MODE_PRIVATE);
        String username = prefs.getString("username", "cegep");
        Integer highscore = prefs.getInt("highscore", 0);

        //Reset les préférences de la session
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "cegep");
        editor.apply();

        loadQuestions();
        loadAnswers();

        tvUsername = findViewById(R.id.tv_username);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnTrue = findViewById(R.id.btn_true);
        btnFalse = findViewById(R.id.btn_false);
        btnShare = findViewById(R.id.btn_share);
        btnConfigure = findViewById(R.id.btn_configure);

        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnConfigure.setOnClickListener(this);

        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);
        tvHighscore = findViewById(R.id.tv_highscore);

        tvHighscore.setText(getString(R.string.tv_highscore) + ": " + highscore);
        tvUsername.setText(getString(R.string.tv_username) + ": " + username);
        currentQuestion = 0;
        score = 0;

        showNewQuestion();
    }

    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btn_previous:
                previousAffimation();
                break;
            case R.id.btn_next:
                nextAffirmation();
                break;
            case R.id.btn_true:
                checkAnswer(true);
                nextAffirmation();
                break;
            case R.id.btn_false:
                checkAnswer(false);
                nextAffirmation();
                break;
            case R.id.btn_share:
                Intent sendScore = new Intent();
                sendScore.setAction(Intent.ACTION_SEND);
                sendScore.putExtra(Intent.EXTRA_TEXT, score);
                sendScore.setType("text/plain");
                startActivity(sendScore);
                break;
            case R.id.btn_configure:
                intent = new Intent(QuizActivity.this, ConfigurationActivity.class);
                startActivity(intent);
                tvUsername.setText(prefs.getString("username", "cegep"));
                break;
        }
    }

    private void previousAffimation(){
        if (currentQuestion > 0)
            currentQuestion--;
        else
            currentQuestion = affirmations.size() - 1;

        showNewQuestion();
    }

    private void nextAffirmation(){
        if (currentQuestion < affirmations.size() - 1)
            currentQuestion++;
        else
            currentQuestion = 0;

        showNewQuestion();
    }

    private void loadQuestions(){
        affirmations = new ArrayList<String>();
        affirmations.add( "Le ciel est bleu.");
        affirmations.add( "L'Argentine est en europe.");
        affirmations.add( "12 x 4 = 46");
        affirmations.add( "Le Canada contient 13 prrovinces.");
        affirmations.add( "Le Canada a été fondé en 1867.");
    }

    private void loadAnswers(){
        answers = new ArrayList<Boolean>();
        answers.add(true);
        answers.add(false);
        answers.add(false);
        answers.add(false);
        answers.add(true);
    }

    private void showNewQuestion(){
        tvQuestion.setText(affirmations.get(currentQuestion));
        correctAnswer = answers.get(currentQuestion);
    }

    private void checkAnswer(Boolean answer){
        if (answer == correctAnswer){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            score++;
            tvScore.setText(String.valueOf(score));
            updateHighScore();
            return;
        }
        Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        if (score > 0){
            score--;
            tvScore.setText(String.valueOf(score));
        }
    }

    private void updateHighScore(){
        Integer highscore = prefs.getInt("highscore", 0);
        if (score > highscore){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
            tvHighscore.setText(getString(R.string.tv_highscore) + ": " +  score.toString());
        }
    }
}