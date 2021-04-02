package com.example.projet_planet_express.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.projet_planet_express.Fragments.TrajetFragment;
import com.example.projet_planet_express.R;

import java.util.Calendar;

import static android.app.PendingIntent.getActivity;

public class AjoutTrajetActivity extends AppCompatActivity {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    //Les EditText
    EditText ville_d;
    EditText ville_a;
    EditText date;

    //Les boutons
    Button annuler;
    Button valider;

    //Sélection de la date
    int lastSelectedYear;
    int lastSelectedMonth;
    int lastSelectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_trajet);

        ville_d = findViewById(R.id.et_depart_ajout_trajet);
        ville_a = findViewById(R.id.et_arrivee_ajout_trajet);

        //Sélection de la date du trajet
        date = findViewById(R.id.et_date_trajet);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        //Bouton annuler
        annuler = findViewById(R.id.btn_annuler_ajout_trajet);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(AjoutTrajetActivity.this, PrincipalActivity.class);
                //startActivity(intent);

                //getActivity(this).getSupportFragmentManager().beginTransaction().replace(R.id.contant_main, new Home()).commit();

            }
        });

        //Bouton valider
        valider = findViewById(R.id.btn_valider_ajout_trajet);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Méthodes privées à l'activité AjoutTrajet
    //Méthode pour sélectionner la date avec un spinner de date
    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(day + "/" + (month+1) + "/" + year);

                lastSelectedYear = year;
                lastSelectedMonth = month;
                lastSelectedDay = day;
            }
        };

        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDay);
        datePickerDialog.show();
    }
}