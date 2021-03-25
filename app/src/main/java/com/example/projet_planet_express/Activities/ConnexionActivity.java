package com.example.projet_planet_express.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet_planet_express.Fragments.BienvenueFragment;
import com.example.projet_planet_express.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConnexionActivity extends AppCompatActivity {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    //Les EditText
    EditText email;
    EditText mdp;

    //Instance Firebase
    private FirebaseAuth authentification;
    private FirebaseUser user;

    //Le bouton
    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //Assignation des editexts
        email = findViewById(R.id.et_identifiant_connexion);
        mdp = findViewById(R.id.et_password_connexion);

        //Récupération de l'instance Firebase pour l'inscription
        authentification = FirebaseAuth.getInstance();

        //Assignation du bouton de validation du formulaire
        valider = findViewById(R.id.btn_valider_connexion);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentification.signInWithEmailAndPassword(email.getText().toString(), mdp.getText().toString())
                        .addOnCompleteListener(ConnexionActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = authentification.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    updateUI(null);
                                }
                            }
                        });
            }

        });
    }

    //Méthodes publiques à l'activité Connexion
    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Authentification réussie", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PrincipalActivity.class));
        } else {
            Toast.makeText(this, "Authentification échouée", Toast.LENGTH_LONG).show();
        }
    }

    //Méthodes privées à l'activité Connexion

}