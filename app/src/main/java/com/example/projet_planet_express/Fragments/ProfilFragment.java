package com.example.projet_planet_express.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

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

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        View v = inflater.inflate(R.layout.fragment_profil, container, false);

        TextView tvNom = v.findViewById(R.id.frag_profil_nom);
        TextView tvPrenom = v.findViewById(R.id.frag_profil_prenom);
        TextView tvEmail = v.findViewById(R.id.frag_profil_id);
        TextView tvDate = v.findViewById(R.id.frag_profil_date);
        TextView tvMdp = v.findViewById(R.id.frag_profil_mdp);

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
                    //System.out.println(postSnapshot.toString());
                    if(postSnapshot.getKey().equals("chauffeur")) {
                        for(DataSnapshot dataChauffeur : postSnapshot.getChildren()) {
                            //System.out.println("test");
                            Chauffeur chauffeur = dataChauffeur.getValue(Chauffeur.class);
                            assert chauffeur != null;
                            if (chauffeur.getEmail().equals(user.getEmail())) {
                                String nom = chauffeur.getNom();
                                String prenom = chauffeur.getPrenom();
                                String email = chauffeur.getEmail();
                                String date = chauffeur.getDate();
                                String mdp = chauffeur.getMdp();
                                tvNom.setText(nom);
                                tvPrenom.setText(prenom);
                                tvEmail.setText(email);
                                tvDate.setText(date);
                                tvMdp.setText(mdp);
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

        return v;
    }
}