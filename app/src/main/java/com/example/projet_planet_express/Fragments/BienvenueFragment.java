package com.example.projet_planet_express.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BienvenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BienvenueFragment extends Fragment {

    //Database
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //TextView
    TextView titre;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String param1; 

    public BienvenueFragment() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BienvenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BienvenueFragment newInstance(String param1, String param2) {
        BienvenueFragment fragment = new BienvenueFragment();
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

            //param1 = getActivity().getIntent().getExtras().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //String texte = getArguments().getString("message");
        View v = inflater.inflate(R.layout.fragment_bienvenue, container, false);
        TextView tv = v.findViewById(R.id.frag_bienvenue_tv_corps);
//        tv.setText(mParam1);

        TextView tv2 = v.findViewById(R.id.frag_bienvenue_tv_corps2);
//        tv2.setText(mParam2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot dataChauffeur : postSnapshot.getChildren()) {
                        Chauffeur chauffeur = dataChauffeur.getValue(Chauffeur.class);
                        assert chauffeur != null;
                        if (chauffeur.getEmail().equals(mParam1)) {
                            String nom = chauffeur.getNom();
                            String prenom = chauffeur.getPrenom();
                            tv.setText(nom);
                            tv2.setText(prenom);
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