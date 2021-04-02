package com.example.projet_planet_express.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet_planet_express.Activities.PrincipalActivity;
import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.Classes.Trajet;
import com.example.projet_planet_express.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormulaireAjoutTrajetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormulaireAjoutTrajetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Gestion des EditText
    EditText ville_d;
    EditText ville_a;
    EditText date;

    //Pour la sélection de date
    int lastSelectedYear;
    int lastSelectedMonth;
    int lastSelectedDay;

    //Pour la base de données
    private FirebaseDatabase database;
    private FirebaseAuth authentification;
    private DatabaseReference refTrajet;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormulaireAjoutTrajetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormulaireAjoutTrajetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormulaireAjoutTrajetFragment newInstance(String param1, String param2) {
        FormulaireAjoutTrajetFragment fragment = new FormulaireAjoutTrajetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulaire_ajout_trajet, container, false);
        ville_d = view.findViewById(R.id.et_depart_ajout_trajet);
        ville_a = view.findViewById(R.id.et_arrivee_ajout_trajet);

        date = view.findViewById(R.id.et_date_trajet);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        Button annuler = view.findViewById(R.id.btn_annuler_ajout_trajet);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Ajout d'un nouveau trajet annulé", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });

        authentification = FirebaseAuth.getInstance();

        Button valider = view.findViewById(R.id.btn_valider_ajout_trajet);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTrajetData(createTrajet());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.principal_frame_layout, new TrajetFragment()).commit();
            }
        });

        return view;
    }

    //Méthode privées au fragment
    private void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int years = calendar.get(Calendar.YEAR);
        int months = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(day + "/" + (months+1) + "/" + years);

                lastSelectedYear = years;
                lastSelectedMonth = months;
                lastSelectedDay = day;
            }
        };

        DatePickerDialog datePickerDialog = null;
        //datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDay);
        datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDay);
        datePickerDialog.show();
    }

    //Méthode pour créer un trajet
    private Trajet createTrajet() {
        String adresse_d = ville_d.getText().toString();
        String adresse_a = ville_a.getText().toString();
        String date_trajet = date.getText().toString();

        String user_email =  authentification.getCurrentUser().getEmail();

        Trajet trajet = new Trajet(adresse_d, adresse_a, date_trajet, user_email);
        return trajet;
    }

    //Méthode pour ajouter les données du trajet à la base de données
    private void createTrajetData(Trajet trajet) {
        database = FirebaseDatabase.getInstance();
        refTrajet = database.getReference("trajet");
        String key = refTrajet.push().getKey();
        refTrajet.push();
        refTrajet.child(key).setValue(trajet);

        Toast.makeText(getContext(), "Ajout du trajet effectuée avec succès", Toast.LENGTH_LONG).show();
    }
}