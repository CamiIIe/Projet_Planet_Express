package com.example.projet_planet_express.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Chauffeur implements Parcelable {

    //private int id = 0;
    private String nom;
    private String prenom;
    private String email;
    private String date;
    private String mdp;

    //Constructeurs
    public Chauffeur() {
        //id++;
    }

    public Chauffeur(String nom, String prenom, String email, String date) {
        //id++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date = date;
    }

    public Chauffeur(String nom, String prenom, String email, String date, String mdp) {
        //id++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date = date;
        this.mdp = mdp;
    }

    protected Chauffeur(Parcel in) {
        //id = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        email = in.readString();
        date = in.readString();
        mdp = in.readString();
    }

    public static final Creator<Chauffeur> CREATOR = new Creator<Chauffeur>() {
        @Override
        public Chauffeur createFromParcel(Parcel in) {
            return new Chauffeur(in);
        }

        @Override
        public Chauffeur[] newArray(int size) {
            return new Chauffeur[size];
        }
    };

    //Getters et Setters des propriétés de la classe
//    public int getId() {
//        return id;
//    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(email);
        dest.writeString(date);
        dest.writeString(mdp);
    }
}
