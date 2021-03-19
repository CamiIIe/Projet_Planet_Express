package com.example.projet_planet_express.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class InscriptionActivity extends AppCompatActivity {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    TextView titre;

    EditText nom;
    EditText prenom;
    EditText identifiant;
    EditText dateNaissance;
    EditText mdp;
    EditText confirmMDP;

    DatePickerDialog pickerDialog;
    int lastSelectedYear;
    int lastSelectedMonth;
    int lastSelectedDay;

    Button valider;
    Button annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        titre = findViewById(R.id.tv_inscription);
        nom = findViewById(R.id.et_nom);
        prenom = findViewById(R.id.et_prenom);
        identifiant = findViewById(R.id.et_identifiant);

        //Date de naissance du chauffeur
        dateNaissance = findViewById(R.id.et_date_naissance);
        dateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 selectDate();
            }
        });

        mdp = findViewById(R.id.et_password);
        confirmMDP = findViewById(R.id.et_confirm_password);

        //Bouton valider
        valider = findViewById(R.id.btn_valider);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refChauffeur = database.getReference("chauffeur");
        valider.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (verif_mdp()) {
                    String key = refChauffeur.push().getKey();
                    Chauffeur chauffeurInscrit = createChauffeur();
                    refChauffeur.child(key).setValue(chauffeurInscrit);
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
    //Méthode pour sélection la date
    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateNaissance.setText(day + "/" + month + "/" + year);

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
        String chauffeur_identifiant = identifiant.getText().toString();
        String chauffeur_date_naissance = dateNaissance.getText().toString(); //TODO Date ou String
        String chauffeur_mdp = mdp.getText().toString();

        Chauffeur chauffeur = new Chauffeur(chauffeur_nom, chauffeur_prenom, chauffeur_identifiant, chauffeur_date_naissance, chauffeur_mdp);
        return chauffeur;
    }

    //Méthode pour comparer les mdp
    private boolean verif_mdp() {
        String mot1 = mdp.getText().toString();
        String mot2 = confirmMDP.getText().toString();

        return mot1.equals(mot2);
    }

    //TODO Méthode pour vérifier que le chauffeur n'est pas déjà enregistré dans la base de données
}