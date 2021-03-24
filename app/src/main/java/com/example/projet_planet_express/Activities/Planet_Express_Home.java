package com.example.projet_planet_express.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projet_planet_express.R;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class Planet_Express_Home extends AppCompatActivity {

    ImageView logo_app;
    Button connexion;
    TextView inscription;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo_app = findViewById(R.id.logo_app);
        connexion = findViewById(R.id.btn_connexion);
        inscription = findViewById(R.id.tv_inscription);

        inscription.setPaintFlags(inscription.getPaintFlags());

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Planet_Express_Home.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Planet_Express_Home.this, ConnexionActivity.class);
                startActivity(intent);
            }

        });
    }
}