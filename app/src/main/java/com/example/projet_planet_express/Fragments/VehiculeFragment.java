package com.example.projet_planet_express.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.Classes.Voiture;
import com.example.projet_planet_express.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
 * Use the {@link VehiculeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehiculeFragment extends Fragment {

    //Database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Instance Firebase
    private FirebaseAuth authentification;
    private FirebaseUser user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VehiculeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehiculeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehiculeFragment newInstance(String param1, String param2) {
        VehiculeFragment fragment = new VehiculeFragment();
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
        View view = inflater.inflate(R.layout.fragment_vehicule, container, false);
        FloatingActionButton btn = view.findViewById(R.id.frag_vehicule_btn_float);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.principal_frame_layout, new FormulaireVehiculeFragment()).commit();
            }
        });

        TextView tvImma = view.findViewById(R.id.frag_vehicule_imma);
        TextView tvMarque = view.findViewById(R.id.frag_vehicule_marque);
        TextView tvModele = view.findViewById(R.id.frag_vehicule_model);
        TextView tvType = view.findViewById(R.id.frag_vehicule_type);
        TextView tvCouleur = view.findViewById(R.id.frag_vehicule_couleur);
        TextView tvAnnee = view.findViewById(R.id.frag_vehicule_annee);
//        TextView tvAucun = view.findViewById(R.id.aucun_vehicule);

        //Récupération de la base de données
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

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
                                    String immatriculation = voiture.getImmatriculation();
                                    String marque = voiture.getMarque();
                                    String modele = voiture.getModele();
                                    String type = voiture.getType();
                                    String couleur = voiture.getCouleur();
                                    String annee = String.valueOf(voiture.getAnnee());

                                    tvImma.setText(immatriculation);
                                    tvMarque.setText(marque);
                                    tvModele.setText(modele);
                                    tvType.setText(type);
                                    tvCouleur.setText(couleur);
                                    tvAnnee.setText(annee);
                                    //tvAucun.setText("");
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

        return view;
    }
}