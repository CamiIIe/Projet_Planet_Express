package com.example.projet_planet_express.Class;

import java.util.Date;

public class Voiture {

    private String immatriculation;
    private String marque;
    private String modele;
    private String type;
    private String couleur;
    private int annee;

    /*
        propositions de voiture pour app
        Compacte, Berline, Cabriolet, Break, 4x4, Monospace
    */

    //Constructeurs
    public Voiture() {

    }

    public Voiture(String immatriculation, String marque, String modele, String type, String couleur, int annee) {
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.type = type;
        this.couleur = couleur;
        this.annee = annee;
    }

    //Getters et Setters
    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
}
