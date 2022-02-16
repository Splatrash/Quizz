package com.cegep_ol.tp1_quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvUsername;
    private TextView tvQuestion;
    private TextView tvScore;

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

        loadQuestions();
        loadAnswers();

        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnTrue = findViewById(R.id.btn_true);
        btnFalse = findViewById(R.id.btn_false);

        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);

        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);

        currentQuestion = 0;
        score = 0;

        showNewQuestion();
    }

    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btn_previous:
                if (currentQuestion > 0)
                    currentQuestion--;
                else
                    currentQuestion = affirmations.size() - 1;

                showNewQuestion();
                break;
            case R.id.btn_next:
                if (currentQuestion < affirmations.size() - 1)
                    currentQuestion++;
                else
                    currentQuestion = 0;

                showNewQuestion();
                break;
            case R.id.btn_true:
                checkAnswer(true);
                break;
            case R.id.btn_false:
                checkAnswer(false);
                break;
            case R.id.btn_share:
                break;
            case R.id.btn_configure:
                break;
        }
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
            return;
        }
        Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
    }
}