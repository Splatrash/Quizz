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



        etUsername = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvError = findViewById(R.id.tv_error);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Intent intent;

                if(!verifyLogin(username, password)){
                    tvError.setText(R.string.loginError);
                }
                else {
                    intent = new Intent(LoginActivity.this, QuizActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public static boolean verifyLogin(String userName, String password){
        if (!"cegep".equals(userName)){
            return false;
        }
        if (!"123".equals(password)){
            return false;
        }
        return true;
    }
}