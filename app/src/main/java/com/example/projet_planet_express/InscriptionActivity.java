package com.example.projet_planet_express;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class InscriptionActivity extends AppCompatActivity {

    EditText nom;
    EditText prenom;
    EditText identifiant;
    EditText dateNaissance;
    EditText mdp;
    EditText confirmMDP;

    DatePickerDialog pickerDialog;

    Button valider;
    Button annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        nom = findViewById(R.id.et_nom);
        prenom = findViewById(R.id.et_prenom);
        identifiant = findViewById(R.id.et_identifiant);
        dateNaissance = findViewById(R.id.et_date_naissance);
        mdp = findViewById(R.id.et_password);
        confirmMDP = findViewById(R.id.et_confirm_password);

        valider = findViewById(R.id.btn_valider);
        annuler = findViewById(R.id.btn_annuler);

        dateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                pickerDialog = new DatePickerDialog(InscriptionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateNaissance.setText(day + " " + month + " " + year);

                    }
                } , year, month, day);
                pickerDialog.show();
            }
        });

    }

    /*
        propositions de voiture pour app
                Compacte, Berline, Cabriolet, Break, 4x4, Monospace
         */
}