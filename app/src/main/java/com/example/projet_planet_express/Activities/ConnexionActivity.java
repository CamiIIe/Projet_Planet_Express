package com.example.projet_planet_express.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.projet_planet_express.R;

public class ConnexionActivity extends AppCompatActivity {

    EditText identifiant;
    EditText mdp;

    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        identifiant = findViewById(R.id.et_identifiant_connexion);
        mdp = findViewById(R.id.et_password_connexion);

        valider = findViewById(R.id.btn_valider_connexion);
    }
}