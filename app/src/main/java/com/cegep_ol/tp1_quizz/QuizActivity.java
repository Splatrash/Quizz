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
    private TextView tvAffirmation;
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

    private Integer currentAffirmation;
    private Integer score;

    private Boolean correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        loadAffirmations();
        loadAnswers();

        findViewsById();
        setOnClickListeners();

        initiatePrefs();

        //Initialise les valeurs initiales du quiz.
        currentAffirmation = 0;
        score = 0;

        showNewAffirmation();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_previous:
                previousAffirmation();
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
                shareScore();
                break;
            case R.id.btn_configure:
                openConfigurations();
                break;
        }
    }

    //Charge l'affirmation précédente, si c'est la première affirmation, charge la dernière affirmation.
    private void previousAffirmation(){
        if (currentAffirmation > 0)
            currentAffirmation--;
        else
            currentAffirmation = affirmations.size() - 1;

        showNewAffirmation();
    }

    //Charge l'affirmation suivante, si c'est la dernière affirmation, charge la première affirmation.
    private void nextAffirmation(){
        if (currentAffirmation < affirmations.size() - 1)
            currentAffirmation++;
        else
            currentAffirmation = 0;

        showNewAffirmation();
    }

    //Charge l'affirmation liée avec la question courante.
    private void showNewAffirmation(){
        tvAffirmation.setText(affirmations.get(currentAffirmation));
        correctAnswer = answers.get(currentAffirmation);
    }

    //Vérifie si la réponse est bonne et mes la score à jour.
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

    //Met à jour le record si le score actuel est plus grand.
    private void updateHighScore(){
        Integer highscore = prefs.getInt("highscore", 0);
        if (score > highscore){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
            tvHighscore.setText(getString(R.string.tv_highscore) + ": " +  score.toString());
        }
    }

    //Ouvre le menu pour partager son score.
    private void shareScore(){
        Intent sendScore = new Intent();
        sendScore.setAction(Intent.ACTION_SEND);
        sendScore.putExtra(Intent.EXTRA_TEXT, score);
        sendScore.setType("text/plain");
        startActivity(sendScore);
    }

    //Ouvre l'activité de configuration.
    private void openConfigurations(){
        Intent intent;

        intent = new Intent(QuizActivity.this, ConfigurationActivity.class);
        startActivity(intent);
        tvUsername.setText(prefs.getString("username", "cegep"));
    }

    //Les affirmations sont stocké ici.
    private void loadAffirmations(){
        affirmations = new ArrayList<String>();
        affirmations.add( "Le ciel est bleu.");
        affirmations.add( "L'Argentine est en europe.");
        affirmations.add( "12 x 4 = 46");
        affirmations.add( "Le Canada contient 13 prrovinces.");
        affirmations.add( "Le Canada a été fondé en 1867.");
        affirmations.add( "Est-ce que la terre est plate.");
        affirmations.add( "Mélanger le rouge et le bleu donne orange.");
        affirmations.add( "Un googol est un 1 suivi de 100 zéros.");
        affirmations.add( "Le corps humain adulte contient 208 os.");
        affirmations.add( "Je mérite 100% sur le travail.");

    }

    //Les réponses des affirmations sont stocké ici, l'ordre qu'ils sont ajouté doit être le même que les affirmations.
    private void loadAnswers(){
        answers = new ArrayList<Boolean>();
        answers.add(true);
        answers.add(false);
        answers.add(false);
        answers.add(false);
        answers.add(true);
        answers.add(false);
        answers.add(false);
        answers.add(true);
        answers.add(false);
        answers.add(false);
    }

    private void findViewsById(){
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnTrue = findViewById(R.id.btn_true);
        btnFalse = findViewById(R.id.btn_false);
        btnShare = findViewById(R.id.btn_share);
        btnConfigure = findViewById(R.id.btn_configure);

        tvUsername = findViewById(R.id.tv_username);
        tvAffirmation = findViewById(R.id.tv_affirmation);
        tvScore = findViewById(R.id.tv_score);
        tvHighscore = findViewById(R.id.tv_highscore);
    }

    private void setOnClickListeners(){
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnConfigure.setOnClickListener(this);
    }

    private void initiatePrefs(){
        prefs = getSharedPreferences("QuizzSave", MODE_PRIVATE);
        String username = prefs.getString("username", "cegep");
        Integer highscore = prefs.getInt("highscore", 0);

        //Reset le username de la session précédente
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "cegep");
        editor.apply();

        tvHighscore.setText(getString(R.string.tv_highscore) + ": " + highscore);
        tvUsername.setText(getString(R.string.tv_username) + ": " + username);

    }

}