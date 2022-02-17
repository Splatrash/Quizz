package com.cegep_ol.tp1_quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsById();

        //Appelé lorque le bouton d'authentification est appuyé.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyLogin();
            }
        });
    }

    //Vérifie les informations de l'utilisateur et charge le quiz si ils sont valide.
    private void verifyLogin(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (!"cegep".equals(username)){
            tvError.setText(R.string.loginError);
            return;
        }
        if (!"123".equals(password)){
            tvError.setText(R.string.loginError);
            return;
        }
        loadQuizz();
    }

    //Charge l'activité du quiz.
    public void loadQuizz(){
        Intent intent;

        intent = new Intent(LoginActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    public void findViewsById(){
        etUsername = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvError = findViewById(R.id.tv_error);
    }

}