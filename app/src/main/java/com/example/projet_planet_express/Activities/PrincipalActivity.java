package com.example.projet_planet_express.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.projet_planet_express.Classes.Chauffeur;
import com.example.projet_planet_express.Fragments.BienvenueFragment;
import com.example.projet_planet_express.Fragments.ColisFragment;
import com.example.projet_planet_express.Fragments.HistoriqueFragment;
import com.example.projet_planet_express.Fragments.ProfilFragment;
import com.example.projet_planet_express.Fragments.TrajetFragment;
import com.example.projet_planet_express.Fragments.VehiculeFragment;
import com.example.projet_planet_express.R;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    /*
    //NavHeader
    ImageView navheader;
    */

    //FrameLayout
    FrameLayout frameLayout;

    //Fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //Instance Firebase
    private FirebaseAuth authentification;
    private FirebaseUser user;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Assignation de la toolbar et création du bouton menu
        toolbar = findViewById(R.id.principal_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Assignation de l'actionBarDrawerToggle et du drawer
        drawer = findViewById(R.id.principal_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(PrincipalActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Assignation de la navigationView et ses méthodes associés
        navigationView = findViewById(R.id.principal_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Récupération de l'instance Firebase pour l'inscription
        authentification = FirebaseAuth.getInstance();
        user = authentification.getCurrentUser();


        //TEST LECTUREBASE




            if(user!=null){
                    String email=user.getDisplayName();
                    String id=user.getEmail();

            getSupportFragmentManager().beginTransaction().add(R.id.principal_frame_layout, BienvenueFragment.newInstance(email, id)).commit();
        };

    }

    //Méthodes publiques à l'activité Principale
    //Méthode pour gérer les fragments et leurs affichages
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.principal_profil) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.principal_frame_layout, new ProfilFragment()).commit();
            drawer.closeDrawer(GravityCompat.START);

        } else if(item.getItemId() == R.id.principal_vehicule) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.principal_frame_layout, new VehiculeFragment()).commit();
            drawer.closeDrawer(GravityCompat.START);

        } else if (item.getItemId() == R.id.principal_trajets) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.principal_frame_layout, new TrajetFragment()).commit();
            drawer.closeDrawer(GravityCompat.START);

        } else if (item.getItemId() == R.id.principal_colis) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.principal_frame_layout, new ColisFragment()).commit();
            drawer.closeDrawer(GravityCompat.START);

        } else if (item.getItemId() == R.id.principal_historique) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.principal_frame_layout, new HistoriqueFragment()).commit();
            drawer.closeDrawer(GravityCompat.START);

        } else if (item.getItemId() == R.id.principal_parametre) {
            Intent intent = new Intent(PrincipalActivity.this, ParametresActivity.class);
            startActivity(intent);

        } else {
            //Permet de déconnecter l'utilisateur et renvoie à l'activité de départ : Planet_Express_Home
            FirebaseAuth.getInstance().signOut();
            FirebaseUser user = authentification.getCurrentUser();
            deconnectUI(user);
        }
        return true;
    }

    //Méthodes privés à l'activité Principale
    //Méthode de déconnexion de l'utilisateur FirebaseUser
    private void deconnectUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Planet_Express_Home.class));
        } else {
            Toast.makeText(this, "Déconnexion échouée", Toast.LENGTH_LONG).show();
        }
    }

    //Méthode pour vérifier que l'utilisateur est bien log
    private boolean isCurrentUserLogged() {
        return (authentification.getCurrentUser() != null);
    }

}