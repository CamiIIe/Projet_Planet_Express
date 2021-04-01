package com.example.projet_planet_express.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.projet_planet_express.R;

public class ParametresActivity extends AppCompatActivity {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    //ImageButton
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        //Bouton back
        btn_back = findViewById(R.id.parametre_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParametresActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }
}