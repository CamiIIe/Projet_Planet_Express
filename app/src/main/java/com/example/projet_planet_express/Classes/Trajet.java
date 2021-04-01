package com.example.projet_planet_express.Classes;

import android.text.format.Time;

import java.util.Date;
import java.util.List;

public class Trajet {

    private String depart;
    private String arrivee;
    private String heure_depart;
    private String heure_arrivee;
    private String date;

    //Attributs pour gérer le tri
    private boolean prochainement = true;

    //Attributs pour gérer les détails
    private String details_d;
    private String details_a;

    //Constructeurs
    public Trajet() {

    }

    public Trajet(String depart, String arrivee, String heure_depart, String heure_arrivee, String date) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.heure_depart = heure_depart;
        this.heure_arrivee = heure_arrivee;
        this.date = date;
    }

    public Trajet(String depart, String arrivee, String heure_depart, String heure_arrivee, String date, String details_d, String details_a) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.heure_depart = heure_depart;
        this.heure_arrivee = heure_arrivee;
        this.date = date;
        this.details_d = details_d;
        this.details_a = details_a;
    }

    //Getters et Setters
    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public String getHeure_arrivee() {
        return heure_arrivee;
    }

    public void setHeure_arrivee(String heure_arrivee) {
        this.heure_arrivee = heure_arrivee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isProchainement() {
        return prochainement;
    }

    public void setProchainement(boolean prochainement) {
        this.prochainement = prochainement;
    }

    //Autres méthodes
    //Méthode qui retourne le titre du trajet
    private String getTitle() {
        if (getDepart().equals(getArrivee())) {
            return "Trajet : "+getDepart().toUpperCase();
        }
        return "Trajet : "+getDepart().toUpperCase() + " -- " + getArrivee().toUpperCase();
    }

}
