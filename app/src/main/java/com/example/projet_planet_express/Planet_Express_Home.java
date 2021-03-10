package com.example.projet_planet_express;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Planet_Express_Home extends AppCompatActivity {

    ImageView logo_app;
    Button connexion;
    TextView inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo_app = findViewById(R.id.logo_app);
        connexion = findViewById(R.id.btn_connexion);
        inscription = findViewById(R.id.tv_inscription);

        inscription.setPaintFlags(inscription.getPaintFlags());

    }
}