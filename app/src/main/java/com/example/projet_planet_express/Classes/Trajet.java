package com.example.projet_planet_express.Classes;

import android.text.format.Time;

import java.util.Date;

public class Trajet {

    private String depart;
    private String arrivee;
    private Time heure_depart;
    private Time heure_arrivee;
    private Date date; //TODO Date ou String ?

    //Constructeurs
    public Trajet() {

    }

    public Trajet(String depart, String arrivee, Time heure_depart, Time heure_arrivee, Date date) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.heure_depart = heure_depart;
        this.heure_arrivee = heure_arrivee;
        this.date = date;
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

    public Time getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(Time heure_depart) {
        this.heure_depart = heure_depart;
    }

    public Time getHeure_arrivee() {
        return heure_arrivee;
    }

    public void setHeure_arrivee(Time heure_arrivee) {
        this.heure_arrivee = heure_arrivee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
