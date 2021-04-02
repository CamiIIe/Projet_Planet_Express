package com.example.projet_planet_express.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.Classes.Trajet;
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

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajetFragment extends Fragment {

    //Pour la base de données
    private FirebaseDatabase database;
    private FirebaseAuth authentification;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrajetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrajetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrajetFragment newInstance(String param1, String param2) {
        TrajetFragment fragment = new TrajetFragment();
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
        View view = inflater.inflate(R.layout.fragment_trajets, container, false);
        boolean[] prochain_trajet = new boolean[1];

        //Gestion de l'affichage ou non des boutons Radio pour le tri des trajets
        RadioButton prochain = view.findViewById(R.id.rb_prochain_trajet);
        RadioButton tous = view.findViewById(R.id.rb_tous_trajet);
        tous.setChecked(true);

        //Gestion de la liste des trajets dans le fragment
        ListView liste = view.findViewById(R.id.lv_trajet);

        ArrayList<String> trajets = new ArrayList<String>();
        ArrayList<Trajet> liste_trajets = new ArrayList<Trajet>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_selectable_list_item, trajets);
        //ArrayAdapter<Trajet> adapter = new ArrayAdapter<Trajet>(getContext(), android.R.layout.simple_list_item_1, trajets);

        authentification = FirebaseAuth.getInstance();
        FirebaseUser user = authentification.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                trajets.clear();
                liste_trajets.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //System.out.println(postSnapshot.toString());
                    if(postSnapshot.getKey().equals("trajet")){
                        for(DataSnapshot data : postSnapshot.getChildren()) {
                            //System.out.println("test");
                            Trajet trajet = data.getValue(Trajet.class);
                            liste_trajets.add(trajet);
                            //System.out.println(trajet.toString());
                            assert trajet != null;
                            if (trajet.getEmail().equals(user.getEmail())) {
                                String titre = trajet.getTitle();
                                //System.out.println(titre);
                                trajets.add(titre);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        /*trajets.add("Trajet 1");
        trajets.add("Trajet 2");
        trajets.add("Trajet 3");
        trajets.add("Trajet 4");
        trajets.add("Trajet 5");
        trajets.add("Trajet 6");*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, trajets);
        liste.setAdapter(adapter);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int nb = liste_trajets.size();
                /*
                switch(position) {
                    case 0:
                        Uri url = Uri.parse("" +
                                "https://www.google.com/maps/dir/?api=1&origin="
                                +liste_trajets.get(0).getDepart()
                                +"&destination="+liste_trajets.get(0).getArrivee()
                                +"&travelmode=driving");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, url);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                }*/

                for (int i = 0; i < nb; i++) {
                    if (i == position) {
                        Uri url = Uri.parse("" +
                                "https://www.google.com/maps/dir/?api=1&origin="
                                +liste_trajets.get(i).getDepart()
                                +"&destination="+liste_trajets.get(i).getArrivee()
                                +"&travelmode=driving");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, url);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }
            }
        });

        FloatingActionButton btn = view.findViewById(R.id.frag_trajet_btn_float);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frame_trajet, new FormulaireAjoutTrajetFragment()).commit();

                liste.setVisibility(View.GONE);
                prochain.setVisibility(View.GONE);
                tous.setVisibility(View.GONE);
            }
        });
        return view;
    }
}