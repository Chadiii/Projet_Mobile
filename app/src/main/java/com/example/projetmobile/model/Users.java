package com.example.projetmobile.model;

import java.io.Serializable;

public class Users implements Serializable {
    private static Users currentUser;

    private String email, login, nom, prenom, password, type, telephone, role;
    public int level;

    public Users(){

    }

    public Users(String email, String nom, String prenom, String telephone, String role){
        this.email = email;
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    public Users(String email, String nom, String prenom, String telephone, String role, int level){
        this.email = email;
        this.telephone = telephone;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.level = level;
    }

    /*public Users(String email, String login, String nom, String prenom, String password, String type) {
        this.email = email;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.type = type;
    }*/

    static public Users getCurrentUser() {
        return currentUser;
    }

    static public void setCurrentUser(Users currentUser) {
        Users.currentUser = currentUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String displayName(){
        return this.getPrenom()+" "+this.getNom();
    }
}
