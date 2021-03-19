package com.example.projet_planet_express.Classes;

public class Chauffeur {

    private static int id = 0;
    private String nom;
    private String prenom;
    private String identifiant;
    private String date;            //TODO Date ou String ?
    private String mdp;

    //Constructeurs
    public Chauffeur() {
        id++;
    }

    public Chauffeur(String nom, String prenom, String identifiant, String date, String mdp) {
        id++;
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.date = date;
        this.mdp = mdp;
    }

    //Getters et Setters des propriétés de la classe
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Chauffeur.id = id;
    }

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

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
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
}
