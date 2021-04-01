package com.example.projet_planet_express.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.projet_planet_express.Activities.PrincipalActivity;
import com.example.projet_planet_express.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormulaireVehiculeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormulaireVehiculeFragment extends Fragment {

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
        Spinner spinner = view.findViewById(R.id.sp_type_vehicule);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_list, android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);

        return view;
    }
}