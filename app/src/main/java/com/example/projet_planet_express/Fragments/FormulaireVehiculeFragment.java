package com.example.projet_planet_express.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_planet_express.Classes.Voiture;
import com.example.projet_planet_express.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormulaireVehiculeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormulaireVehiculeFragment extends Fragment {

    //Les EditText
    EditText immatriculation;
    EditText marque;
    EditText modele;
    EditText couleur;
    EditText annee;

    Spinner spinner;

    String vehiculeKey;
    Boolean keyIsSet = false;
    Boolean[] isSet = {false};

    //Bouton
    Button btn_valider_vehicule;

    //L'instance de Firebase
    private FirebaseDatabase database;
    private FirebaseAuth authentification;
    private FirebaseUser user;
    private DatabaseReference refVoiture;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormulaireVehiculeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormulaireVehiculeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormulaireVehiculeFragment newInstance(String param1, String param2) {
        FormulaireVehiculeFragment fragment = new FormulaireVehiculeFragment();
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
        View view = inflater.inflate(R.layout.fragment_formulaire_vehicule, container, false);
        spinner = view.findViewById(R.id.sp_type_vehicule);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_list, android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);

        authentification = FirebaseAuth.getInstance();

        //Assignation des editexts
        immatriculation = view.findViewById(R.id.et_immatriculation);
        marque = view.findViewById(R.id.et_marque_vehicule);
        modele = view.findViewById(R.id.et_modele_vehicule);
        couleur = view.findViewById(R.id.et_couleur_vehicule);
        annee = view.findViewById(R.id.et_annee_vehicule);

        //Si vehicule dans la base de données, préremplissage des EditTexts
        //immatriculation.setText("Immatriculation", TextView.BufferType.EDITABLE);

        remplissageEditText();

        if(immatriculation.getText().toString().equals("")){
            btn_valider_vehicule = view.findViewById(R.id.btn_valider_vehicule);
            btn_valider_vehicule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Création du vehicule et enregistrement dans la BD
                    createVehiculeData(createVoiture());
                    Toast.makeText(view.getContext(), "Ajout d'un nouveau véhicule effectué avec succès", Toast.LENGTH_LONG).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.principal_frame_layout, new VehiculeFragment()).commit();
                }
            });
        } else {
            btn_valider_vehicule = view.findViewById(R.id.btn_valider_vehicule);
            btn_valider_vehicule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Création du vehicule et enregistrement dans la BD
                    updateVehicule();
                    Toast.makeText(view.getContext(), "Mise à jour du véhicule effectué avec succès", Toast.LENGTH_LONG).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.principal_frame_layout, new VehiculeFragment()).commit();
                }
            });
        }
/*
        //Assignation bouton et traitement onClick
        btn_valider_vehicule = view.findViewById(R.id.btn_valider_vehicule);
        btn_valider_vehicule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(immatriculation.getText().toString().equals("")){
                    //Création du vehicule et enregistrement dans la BD
                    createVehiculeData(createVoiture());
                    Toast.makeText(view.getContext(), "Ajout d'un nouveau véhicule effectué avec succès", Toast.LENGTH_LONG).show();

                } else {
                    //Mise à jour du vehicule
                    updateVehicule();

                    Toast.makeText(view.getContext(), "Mise à jour du véhicule effectué avec succès", Toast.LENGTH_LONG).show();

                }
                //Création du vehicule et enregistrement dans la BD
//                createVehiculeData(createVoiture());
//
//                Toast.makeText(view.getContext(), "Ajout d'un nouveau véhicule effectué avec succès", Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.principal_frame_layout, new VehiculeFragment()).commit();
            }
        });*/

        return view;
    }

    //Méthode pour créer une voiture
    private Voiture createVoiture() {
        String voiture_immatriculation = immatriculation.getText().toString();
        String voiture_marque = marque.getText().toString();
        String voiture_modele = modele.getText().toString();
        String voiture_type = spinner.getSelectedItem().toString();
        String voiture_couleur = couleur.getText().toString();
        int voiture_annee = Integer.parseInt(annee.getText().toString());
        String user_email = authentification.getCurrentUser().getEmail();

        return new Voiture(voiture_immatriculation, voiture_marque, voiture_modele, voiture_type,
                voiture_couleur, voiture_annee, user_email);
    }

    //Méthode pour ajouter les données du chauffeur à la base de données
    private void createVehiculeData(Voiture voiture) {

        //Database
        database = FirebaseDatabase.getInstance();
        refVoiture = database.getReference("voiture");
        String key = refVoiture.push().getKey();
        refVoiture.push();
        refVoiture.child(key).setValue(voiture);
    }

    //Méthode pour ajouter les données du chauffeur à la base de données
    private void createVehiculeDataWithKey(Voiture voiture, String vehiculeKey) {

        //Database
        database = FirebaseDatabase.getInstance();
        refVoiture = database.getReference("voiture");
        //String key = refVoiture.push().getKey();
        refVoiture.push();
        refVoiture.child(vehiculeKey).setValue(voiture);
    }

    private void updateVehicule() {
        //Récupération de la base de données
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        //Récupération de l'instance Firebase pour l'inscription
        authentification = FirebaseAuth.getInstance();
        user = authentification.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().equals("voiture")){
                        for(DataSnapshot dataVoiture : postSnapshot.getChildren()) {
                            Voiture voiture = dataVoiture.getValue(Voiture.class);
                            if(voiture != null) {
                                if (voiture.getEmail_user().equals(user.getEmail())) {
                                    dataVoiture.getRef().child("immatriculation").
                                            setValue(immatriculation.getText().toString());
                                    dataVoiture.getRef().child("marque").
                                            setValue(marque.getText().toString());
                                    dataVoiture.getRef().child("modele").
                                            setValue(modele.getText().toString());
                                    dataVoiture.getRef().child("type").
                                            setValue(spinner.getSelectedItem().toString());
                                    dataVoiture.getRef().child("couleur").
                                            setValue(couleur.getText().toString());
                                    dataVoiture.getRef().child("annee").
                                            setValue(Integer.parseInt(annee.getText().toString()));
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    private Boolean isSetVehicule() {
        //Récupération de la base de données
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        //Récupération de l'instance Firebase pour l'inscription
        authentification = FirebaseAuth.getInstance();
        user = authentification.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().equals("voiture")){
                        for(DataSnapshot dataVoiture : postSnapshot.getChildren()) {
                            Voiture voiture = dataVoiture.getValue(Voiture.class);
                            if(voiture != null) {
                                if (voiture.getEmail_user().equals(user.getEmail())) {
                                    isSet[0] = true;
                                    System.out.println("A l'intérieur : " + isSet[0]);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
        System.out.println("Avant return : " + isSet[0]);
        return isSet[0];
    }

    private void remplissageEditText() {
        //Récupération de la base de données
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        //Récupération de l'instance Firebase pour l'inscription
        authentification = FirebaseAuth.getInstance();
        user = authentification.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().equals("voiture")){
                        for(DataSnapshot dataVoiture : postSnapshot.getChildren()) {
                            Voiture voiture = dataVoiture.getValue(Voiture.class);
                            if(voiture != null) {
                                if (voiture.getEmail_user().equals(user.getEmail())) {
                                    immatriculation.setText(voiture.getImmatriculation(), TextView.BufferType.EDITABLE);
                                    marque.setText(voiture.getMarque(), TextView.BufferType.EDITABLE);
                                    modele.setText(voiture.getModele(), TextView.BufferType.EDITABLE);
                                    couleur.setText(voiture.getCouleur(), TextView.BufferType.EDITABLE);
                                    annee.setText(String.valueOf(voiture.getAnnee()), TextView.BufferType.EDITABLE);
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }
}