package com.example.projet_planet_express.Classes;

public class Chauffeur {

    private int id = 0;
    private String nom;
    private String prenom;
    private String email;
    private String date;
    private String mdp;

    //TODO Liste de trajets ?
    //List<Trajet> trajets;

    //Constructeurs
    public Chauffeur() {
        id++;
    }

    public Chauffeur(String nom, String prenom, String email, String date) {
        id++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date = date;
    }

    public Chauffeur(String nom, String prenom, String email, String date, String mdp) {
        id++;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.date = date;
        this.mdp = mdp;
    }

    //Getters et Setters des propriétés de la classe
    public int getId() {
        return id;
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
}
