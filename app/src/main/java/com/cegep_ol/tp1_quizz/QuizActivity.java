package com.cegep_ol.tp1_quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvQuestion;
    private TextView tvScore;

    private Button btnPrevious;
    private Button btnNext;
    private Button btnTrue;
    private Button btnFalse;
    private Button btnShare;
    private Button btnConfigure;

    private String[] questions;
    private Boolean[] answers;

    private Boolean correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        loadQuestions();
        loadAnswers();

        prepareQuestion(0);
    }

    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btn_previous:
                return;
            case R.id.btn_next:
                return;
            case R.id.btn_true:
                return;
            case R.id.btn_false:
                return;
            case R.id.btn_share:
                return;
            case R.id.btn_configure:
                return;
        }
    }

    private void loadQuestions(){
        questions = new String[]{getString(R.string.question1),
                getString(R.string.question2),
                getString(R.string.question3),
                getString(R.string.question4),
                getString(R.string.question5)};
    }

    private void loadAnswers(){
        answers = new Boolean[]{Boolean.parseBoolean(getString(R.string.answer1)),
                Boolean.parseBoolean(getString(R.string.answer2)),
                Boolean.parseBoolean(getString(R.string.answer3)),
                Boolean.parseBoolean(getString(R.string.answer4)),
                Boolean.parseBoolean(getString(R.string.answer5))};
    }

    private void prepareQuestion(int questionNumber){
        tvQuestion = findViewById(R.id.tv_question);
        tvQuestion.setText(questions[questionNumber]);
        correctAnswer = answers[questionNumber];
    }
}