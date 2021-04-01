package com.example.projet_planet_express.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

public class InscriptionActivity extends AppCompatActivity {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    TextView titre;

    //Les EditText
    EditText nom;
    EditText prenom;
    EditText email;
    EditText dateNaissance;
    EditText mdp;
    EditText confirmMDP;

    //Le DatePicker
    DatePickerDialog pickerDialog;
    int lastSelectedYear;
    int lastSelectedMonth;
    int lastSelectedDay;

    //L'instance de Firebase
    private FirebaseDatabase database;
    private FirebaseAuth authentification;
    private DatabaseReference refChauffeur;

    //Les Boutons
    Button valider;
    Button annuler;

    private static final String TAG = "Your Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //Assignation des editexts
        titre = findViewById(R.id.tv_inscription);
        nom = findViewById(R.id.et_nom);
        prenom = findViewById(R.id.et_prenom);
        email = findViewById(R.id.et_email);

        //Date de naissance du chauffeur
        dateNaissance = findViewById(R.id.et_date_naissance);
        dateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 selectDate();
            }
        });

        //Assignation des mot de passe
        mdp = findViewById(R.id.et_password);
        confirmMDP = findViewById(R.id.et_confirm_password);

        //Récupération de l'instance Firebase pour l'inscription
        authentification = FirebaseAuth.getInstance();

        //Bouton valider
        valider = findViewById(R.id.btn_valider);

        //Database
        database = FirebaseDatabase.getInstance();
        refChauffeur = database.getReference("chauffeur");

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verif_mdp()) {
                    authentification.createUserWithEmailAndPassword(email.getText().toString(), mdp.getText().toString())
                                    .addOnCompleteListener(InscriptionActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = authentification.getCurrentUser();
                                                createUserData(createChauffeur());
                                                updateUI(user);
                                            } else {
                                                updateUI(null);
                                            }
                                        }
                                    });

                } else {
                    titre.setText("Erreur lors de la vérification des mots de passe");
                }
            }
        });

        //Bouton annuler
        annuler = findViewById(R.id.btn_annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionActivity.this, Planet_Express_Home.class);
                startActivity(intent);
            }
        });

    }

    //Méthodes privées à l'activité Inscription
    //Méthode pour sélectionner la date avec un spinner de date
    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateNaissance.setText(day + "/" + (month+1) + "/" + year);

                lastSelectedYear = year;
                lastSelectedMonth = month;
                lastSelectedDay = day;
            }
        };

        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDay);
        datePickerDialog.show();
    }

    //Méthode pour créer un chauffeur
    private Chauffeur createChauffeur() {
        String chauffeur_nom = nom.getText().toString();
        String chauffeur_prenom = prenom.getText().toString();
        String chauffeur_email = email.getText().toString();
        String chauffeur_mdp = mdp.getText().toString();
        String chauffeur_date_naissance = dateNaissance.getText().toString();

        return new Chauffeur(chauffeur_nom, chauffeur_prenom, chauffeur_email, chauffeur_date_naissance, chauffeur_mdp);
    }

    //Méthode pour comparer les mdp
    private boolean verif_mdp() {
        String mot1 = mdp.getText().toString();
        String mot2 = confirmMDP.getText().toString();

        return mot1.equals(mot2);
    }

    //Méthode pour ajouter les données du chauffeur à la base de données
    private void createUserData(Chauffeur chauffeur) {
        String key = refChauffeur.push().getKey();
        refChauffeur.push();
        Chauffeur chauffeurInscrit = createChauffeur();
        refChauffeur.child(key).setValue(chauffeurInscrit);
    }

    //Méthodes publiques de l'activité Inscription
    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Inscription effectuée avec succès", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PrincipalActivity.class));
        } else {
            Toast.makeText(this, "Inscription échouée", Toast.LENGTH_LONG).show();
        }
    }
}