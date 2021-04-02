package com.example.projet_planet_express.Classes;

import java.util.Date;

public class Colis {

    private int numero;
    private String nom_destinataire;
    private String prenom_destinataire;
    private double poids;
    private String statut;
    private String date_reception;
    private String date_livraison;
    private String addresse_livraison;

    private String email;

    //Constructeurs
    public Colis() {

    }

    public Colis(String nom_destinataire, String prenom_destinataire, double poids, String date_reception, String mail) {
        this.nom_destinataire = nom_destinataire;
        this.prenom_destinataire = prenom_destinataire;
        this.poids = poids;
        this.date_reception = date_reception;
        this.email = mail;
    }

    //Getters et Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom_destinataire() {
        return nom_destinataire;
    }

    public void setNom_destinataire(String nom_destinataire) {
        this.nom_destinataire = nom_destinataire;
    }

    public String getPrenom_destinataire() {
        return prenom_destinataire;
    }

    public void setPrenom_destinataire(String prenom_destinataire) {
        this.prenom_destinataire = prenom_destinataire;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDate_reception() {
        return date_reception;
    }

    public void setDate_reception(String date_reception) {
        this.date_reception = date_reception;
    }

    public String getDate_livraison() {
        return date_livraison;
    }

    public void setDate_livraison(String date_livraison) {
        this.date_livraison = date_livraison;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddresse_livraison() {
        return addresse_livraison;
    }

    public void setAddresse_livraison(String addresse_livraison) {
        this.addresse_livraison = addresse_livraison;
    }

    //Autres méthodes
    //Méthode qui retourne le titre du trajet
    public String getTitle() {
        return "Colis : "+getPrenom_destinataire().toUpperCase() + " -- " + getNom_destinataire().toUpperCase();
    }
}
