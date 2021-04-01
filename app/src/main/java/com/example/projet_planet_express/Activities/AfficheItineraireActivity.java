package com.example.projet_planet_express.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.projet_planet_express.Classes.Trajet;
import com.example.projet_planet_express.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Calendar;

public class AfficheItineraireActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Les assignations des propriétés au layout et les méthodes OnClick sont à la suite comme dans l'ordre de déclaration des propriétés
    //Les méthodes OnClick sont placés juste en dessous des assignations des layouts aux propriétés

    //Edit Text
    EditText depart;
    EditText arrivee;
    EditText heure_depart;
    EditText heure_arrivee;

    //Buttons
    Button annuler;
    Button valider;

    //Propriétés pour afficher l'heure
    int hour;
    int minutes;

    //Affichage de la location de l'utilisateur
    private PlacesClient placesClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;

    private GoogleMap map;
    private GoogleMapOptions options;
    private CameraPosition cameraPosition;
    private Location lastKnownLocation;

    //Geocoder pour convertir une adresse en coordonnées gps et vice-versa
    private Geocoder geocoder;

    //Propriétés statiques de l'activité
    private static final int PERMISSION_REQUEST_ACCES_FINE_LOCATION = 1;
    private static final String TAG = AfficheItineraireActivity.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 10;

    //Définition de la location par défaut à Tours
    private final LatLng defaultLocation = new LatLng(47.383333, 0.68484);

    //Enregistrer l'état de la carte
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    //Méthode de status de l'activité
    //Méthode de création de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_trajet);

        //Assignation des EditTexts
        depart = findViewById(R.id.et_depart_ajout_trajet);
        arrivee = findViewById(R.id.et_arrivee_ajout_trajet);

        /*
        //Assignation et gestion des EditText pour saisir l'heure du trajet
        heure_depart = findViewById(R.id.ed_heure_d);
        heure_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTimeDepart();
            }
        });

        heure_arrivee = findViewById(R.id.ed_heure_a);
        heure_arrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTimeArrivee();
            }
        });
        */

        //Sauvegarde de la maps en cas de "pause" de l'application
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        //Construction du client Places et du FusedLocationProviderClient pour afficher la localisation de l'utilisateur
        Places.initialize(getApplicationContext(), getString(R.string.maps_api_key));
        placesClient = Places.createClient(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Gestion et affichage de la carte Maps dans le fragment du layout de l'activité
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frame_map);
        mapFragment.getMapAsync(this);

        //Assignation et gestion du bouton annuler
        annuler = findViewById(R.id.btn_annuler_ajout_trajet);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfficheItineraireActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });

        //Assignation et gestion du bouton valider
        valider = findViewById(R.id.btn_valider_ajout_trajet);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //Doit ajouter l'itinéraires à la base de données l'afficher sur maps
                String pos_depart = depart.getText().toString();
                String pos_arrivee = arrivee.getText().toString();

                String time_d = heure_depart.getText().toString();
                String time_a = heure_arrivee.getText().toString();

                //geocoder = new Geocoder(AfficheItineraireActivity.this, Locale.FRANCE);
                try {
                    //HashMap<String, List<Address>> coordonnées_trajets = new HashMap<>();

                    //List<Address> addresse_depart = geocoder.getFromLocationName(pos_depart, 1);
                    //List<Address> addresse_arrivee = geocoder.getFromLocationName(pos_arrivee, 1);

                    /*String url = "https://www.google.com/maps/dir/?api=1&origin="
                            + addresse_depart.get(0).getLatitude() + "," + addresse_depart.get(0).getLongitude() + "&destination="
                            + addresse_arrivee.get(0).getLatitude() + "," + addresse_arrivee.get(0).getLongitude() + "&travelmode=driving";
                    */

                    Uri url = Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+pos_depart+"&destination="+pos_arrivee+"&travelmode=driving");
                    //Création de l'intent qui permet de renvoyer vers Maps pour afficher l'itinéraire de la course
                    //Uri gmmIntentUri = Uri.parse(url);
                    Uri gmmIntentUri = url;
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //Méthode de sauvegarde de l'activité
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    //Méthodes privées de l'activité AjoutTrajet
    //Méthode  pour sélectionner l'heure avec un spinner 24h pour l'horaire de départ
    private void selectTimeDepart() {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                heure_depart.setText(hourOfDay + " : " + minute);
                hour = hourOfDay;
                minutes = minute;
            }
        };

        TimePickerDialog timePickerDialog = null;
        timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, timeSetListener, hour, minutes, true);
        timePickerDialog.show();
    }

    //Méthode  pour sélectionner l'heure avec un spinner 24h pour l'horaire d'arrivée
    private void selectTimeArrivee() {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                heure_arrivee.setText(hourOfDay + " : " + minute);
                hour = hourOfDay;
                minutes = minute;
            }
        };

        TimePickerDialog timePickerDialog = null;
        timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, timeSetListener, hour, minutes, true);
        timePickerDialog.show();
    }

    //Méthode pour transformer la saisie de l'utilisateur en titre pour la liste de trajet
    private String getTitleTrajet(String depart, String arrivee) {
        String[] saisie_depart = depart.split(",");
        String[] saisie_arrivee = arrivee.split(",");

        String ville_d = saisie_depart[saisie_depart.length -1];
        String ville_a = saisie_arrivee[saisie_arrivee.length -1];

        String titre = "Trajet : "+ ville_d.toUpperCase() + " - " + ville_a.toUpperCase();
        return titre;
    }

    //Méthode pour créer un objet Trajet
    private Trajet createTrajet(String depart, String arrivee, String temps_d, String temps_a) {
        //Récupération et découpage de la saisie
        String[] saisie_depart = depart.split(",");
        String[] saisie_arrivee = arrivee.split(",");

        //Détermine les villes de destinations
        String ville_d = saisie_depart[saisie_depart.length -1];
        String ville_a = saisie_arrivee[saisie_arrivee.length -1];

        //Transforme la date pour qu'elle soit manipulable ensuite
        String date = getDateInString();

        //Création de l'objet trajet
        Trajet trajet = new Trajet(ville_d, ville_a, temps_d, temps_a, date, depart, arrivee);
        return trajet;
    }

    //Méthode pour avoir la date
    private String getDateInString() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return day + "/" + (month+1) + "/" + year;
    }

    //Méthode pour avoir la permission de géolocaliser l'appareil par l'utilisateur
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCES_FINE_LOCATION);
        }
    }

    //Méthode pour définir les commandes de position sur la carte
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception : %s", e.getMessage());
        }
    }

    //Méthode pour obtenir la position de l'appareil et positionner la carte
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Met la position de la caméra au niveau de la location courante de l'appareil
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    //Méthodes publiques de l'activité AjoutTrajet
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        this.map = googleMap;
        map.addMarker(new MarkerOptions().position(defaultLocation).title("Repère à Tours"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        updateLocationUI();
        getDeviceLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSION_REQUEST_ACCES_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
}