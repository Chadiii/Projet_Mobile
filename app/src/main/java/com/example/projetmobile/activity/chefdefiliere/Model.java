package com.example.projetmobile.activity.chefdefiliere;

public class Model {

    String nom, prenom, email1;
    String telephone;

    public Model(){

    }

    public Model(String nom, String prenom, String email1, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email1 = email1;
        this.telephone = telephone;
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

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
