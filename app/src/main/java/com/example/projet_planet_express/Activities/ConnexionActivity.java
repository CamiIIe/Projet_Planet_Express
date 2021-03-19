package com.example.projet_planet_express.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.projet_planet_express.R;

public class ConnexionActivity extends AppCompatActivity {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    EditText identifiant;
    EditText mdp;

    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //Assignation des editexts
        identifiant = findViewById(R.id.et_identifiant_connexion);
        mdp = findViewById(R.id.et_password_connexion);

        //Assignation du bouton de validation du formulaire
        valider = findViewById(R.id.btn_valider_connexion);
    }
}