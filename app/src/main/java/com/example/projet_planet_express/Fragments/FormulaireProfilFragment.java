package com.example.projet_planet_express.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_planet_express.Classes.Chauffeur;
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
 * Use the {@link FormulaireProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormulaireProfilFragment extends Fragment {

    //Les EditText
    EditText nom;
    EditText prenom;
    EditText email;
    EditText date;
    EditText mdp;
    EditText mdp2;

    //Bouton
    Button btn_valider_profil;

    //L'instance de Firebase
    private FirebaseDatabase database;
    private FirebaseAuth authentification;
    private FirebaseUser user;
    private DatabaseReference refUser;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormulaireProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormulaireProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormulaireProfilFragment newInstance(String param1, String param2) {
        FormulaireProfilFragment fragment = new FormulaireProfilFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_formulaire_profil, container, false);

        authentification = FirebaseAuth.getInstance();

        //Assignation des editexts
        nom = view.findViewById(R.id.et_nomM);
        prenom = view.findViewById(R.id.et_prenomM);
        email = view.findViewById(R.id.et_emailM);
        date = view.findViewById(R.id.et_date_naissanceM);
        mdp = view.findViewById(R.id.et_passwordM);

        remplissageEditText();

        btn_valider_profil = view.findViewById(R.id.btn_validerM);
        btn_valider_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Création du vehicule et enregistrement dans la BD
                updateProfil();
                Toast.makeText(view.getContext(), "Mise à jour du profil effectué avec succès", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.principal_frame_layout, new ProfilFragment()).commit();
            }
        });

        return view;
    }

    private void updateProfil() {
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
                    if(postSnapshot.getKey().equals("chauffeur")){
                        for(DataSnapshot dataChauffeur : postSnapshot.getChildren()) {
                            Chauffeur chauffeur = dataChauffeur.getValue(Chauffeur.class);
                            if(chauffeur != null) {
                                if (chauffeur.getEmail().equals(user.getEmail())) {
                                    dataChauffeur.getRef().child("nom").
                                            setValue(nom.getText().toString());
                                    dataChauffeur.getRef().child("prenom").
                                            setValue(prenom.getText().toString());
                                    dataChauffeur.getRef().child("email").
                                            setValue(email.getText().toString());
                                    dataChauffeur.getRef().child("date").
                                            setValue(date.getText().toString());
                                    dataChauffeur.getRef().child("mdp").
                                            setValue(mdp.getText().toString());
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
                    if(postSnapshot.getKey().equals("chauffeur")){
                        for(DataSnapshot dataChauffeur : postSnapshot.getChildren()) {
                            Chauffeur chauffeur = dataChauffeur.getValue(Chauffeur.class);
                            if(chauffeur != null) {
                                if (chauffeur.getEmail().equals(user.getEmail())) {
                                    nom.setText(chauffeur.getNom(), TextView.BufferType.EDITABLE);
                                    prenom.setText(chauffeur.getPrenom(), TextView.BufferType.EDITABLE);
                                    email.setText(chauffeur.getEmail(), TextView.BufferType.EDITABLE);
                                    date.setText(chauffeur.getDate(), TextView.BufferType.EDITABLE);
                                    mdp.setText(chauffeur.getMdp(), TextView.BufferType.EDITABLE);
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