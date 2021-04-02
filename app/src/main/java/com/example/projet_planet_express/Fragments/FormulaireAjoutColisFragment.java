package com.example.projet_planet_express.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

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
import com.example.projet_planet_express.Classes.Colis;
import com.example.projet_planet_express.Classes.Trajet;
import com.example.projet_planet_express.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormulaireAjoutColisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormulaireAjoutColisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Propriétés du fragment
    EditText nom_dest;
    EditText prenom_dest;
    EditText poids;
    EditText date;
    EditText adresse_colis;
    Button btn_annuler;
    Button btn_valider;

    //Le DatePicker
    DatePickerDialog pickerDialog;
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

    public FormulaireAjoutColisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormulaireAjoutColisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormulaireAjoutColisFragment newInstance(String param1, String param2) {
        FormulaireAjoutColisFragment fragment = new FormulaireAjoutColisFragment();
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
        View view = inflater.inflate(R.layout.fragment_formulaire_ajout_colis, container, false);

        nom_dest = view.findViewById(R.id.et_nom_destinataire_colis);
        prenom_dest = view.findViewById(R.id.et_prenom_destinataire_colis);
        poids = view.findViewById(R.id.et_poids_ajout_colis);
        adresse_colis = view.findViewById(R.id.et_adresse_colis);
        date = view.findViewById(R.id.et_date_colis);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        btn_annuler = view.findViewById(R.id.btn_annuler_ajout_colis);
        btn_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Ajout d'un nouveau colis annulé", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });

        authentification = FirebaseAuth.getInstance();

        btn_valider = view.findViewById(R.id.btn_valider_ajout_colis);
        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createColisData(createColis());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.principal_frame_layout, new ColisFragment()).commit();
            }
        });
        return view;
    }

    //Méthode pour créer un colis
    private Colis createColis() {
        String nom = nom_dest.getText().toString();
        String prenom = prenom_dest.getText().toString();

        //String pds = poids.getText().toString();
        double poids_colis = Double.parseDouble(poids.getText().toString());
        String adresse = adresse_colis.getText().toString();
        String date_colis = date.getText().toString();

        String user_email = authentification.getCurrentUser().getEmail();
        Colis colis = new Colis(nom, prenom, poids_colis, date_colis, adresse, user_email);
        return colis;
    }

    //Méthode pour ajouter les données du colis à la base de données
    private void createColisData(Colis colis) {
        database = FirebaseDatabase.getInstance();
        refTrajet = database.getReference("colis");
        String key = refTrajet.push().getKey();
        refTrajet.push();
        refTrajet.child(key).setValue(colis);

        Toast.makeText(getContext(), "Ajout du colis effectuée avec succès", Toast.LENGTH_LONG).show();
    }

    //Méthode pour sélectionner la date avec un spinner de date
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
        datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDay);
        datePickerDialog.show();
    }
}