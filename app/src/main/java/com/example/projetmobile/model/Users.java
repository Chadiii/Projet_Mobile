package com.example.projetmobile.model;

public class Users {

    private String email, login, nom, prenom, password;

    public Users(){

    }

    public Users(String email, String login, String nom, String prenom, String password) {
        this.email = email;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
