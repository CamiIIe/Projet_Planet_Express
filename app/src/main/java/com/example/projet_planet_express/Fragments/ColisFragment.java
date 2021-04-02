package com.example.projet_planet_express.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.projet_planet_express.Classes.Colis;
import com.example.projet_planet_express.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Pour la base de données
    private FirebaseDatabase database;
    private FirebaseAuth authentification;
    private DatabaseReference databaseReference;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColisFragment newInstance(String param1, String param2) {
        ColisFragment fragment = new ColisFragment();
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
        View view = inflater.inflate(R.layout.fragment_colis, container, false);
        RadioButton btn_tous = view.findViewById(R.id.rb_livre_colis);
        btn_tous.setChecked(true);
        RadioButton btn_venir = view.findViewById(R.id.rb_prochain_colis);

        ListView liste_colis = view.findViewById(R.id.list_colis);
        ArrayList<String> colis_list = new ArrayList<String>();
        ArrayList<Colis> liste_colis_data = new ArrayList<Colis>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item, colis_list);

        authentification = FirebaseAuth.getInstance();
        FirebaseUser user = authentification.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                colis_list.clear();
                liste_colis_data.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //System.out.println(postSnapshot.toString());
                    if(postSnapshot.getKey().equals("colis")){
                        for(DataSnapshot data : postSnapshot.getChildren()) {
                            //System.out.println("test");
                            Colis colis = data.getValue(Colis.class);
                            liste_colis_data.add(colis);
                            //System.out.println(colis.toString());
                            assert colis != null;
                            if (colis.getEmail().equals(user.getEmail())) {
                                String titre = colis.getTitle();
                                //System.out.println(titre);
                                colis_list.add(titre);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // [START_EXCLUDE]
                System.out.println("The read failed: " + error.getMessage());
            }
        });
        liste_colis.setAdapter(adapter);
        liste_colis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int nb = liste_colis_data.size();
                for (int i = 0; i < nb; i++) {
                    if (i == position) {
                        Uri url = Uri.parse("" +
                                "https://www.google.com/maps/search/?api=1&query="
                                +liste_colis_data.get(i).getAddresse_livraison());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, url);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }
            }
        });

        FloatingActionButton btn_colis = view.findViewById(R.id.frag_colis_btn_float);
        btn_colis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_colis, new FormulaireAjoutColisFragment()).commit();

                liste_colis.setVisibility(View.GONE);
                btn_tous.setVisibility(View.GONE);
                btn_venir.setVisibility(View.GONE);
            }
        });
        return view;
    }
}