package com.example.projet_planet_express.Classes;

import java.util.Date;

public class Colis {

    private static int numero = 0;
    private String nom_destinataire;
    private String prenom_destinataire;
    private double poids;
    private String statut;
    private Date date_reception;    //TODO Date ou string
    private Date date_livraison;    //TODO Date ou string

    //Constructeurs
    public Colis() {

    }

    public Colis(String nom_destinataire, String prenom_destinataire, double poids, String statut, Date date_reception, Date date_livraison) {
        this.nom_destinataire = nom_destinataire;
        this.prenom_destinataire = prenom_destinataire;
        this.poids = poids;
        this.statut = statut;
        this.date_reception = date_reception;
        this.date_livraison = date_livraison;
    }

    //Getters et Setters
    public static int getNumero() {
        return numero;
    }

    public static void setNumero(int numero) {
        Colis.numero = numero;
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

    public Date getDate_reception() {
        return date_reception;
    }

    public void setDate_reception(Date date_reception) {
        this.date_reception = date_reception;
    }

    public Date getDate_livraison() {
        return date_livraison;
    }

    public void setDate_livraison(Date date_livraison) {
        this.date_livraison = date_livraison;
    }
}
