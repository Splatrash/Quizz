package com.cegep_ol.tp1_quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private EditText etNewUsername;
    private Button btnValidate;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("QuizzSave", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        etNewUsername = findViewById(R.id.et_newUsername);
        btnValidate = findViewById(R.id.btn_validate);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = etNewUsername.getText().toString();

                if (newUsername.trim().length() > 0){

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", etNewUsername.getText().toString());
                    editor.apply();

                    intent = new Intent(ConfigurationActivity.this, QuizActivity.class);startActivity(intent);
                }
                else
                    Toast.makeText(ConfigurationActivity.this, R.string.toast_noUsername, Toast.LENGTH_SHORT).show();

            }
        });


    }
}