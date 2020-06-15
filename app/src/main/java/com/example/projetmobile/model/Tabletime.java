package com.example.projetmobile.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class Tabletime {


    private String heuredebut,heurefin, matiere, enseignant, salle, jour, année,id;


    public Tabletime(){

    }

    public Tabletime(String heuredebut, String heurefin, String matiere, String enseignant, String salle, String jour, String année, String id) {
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
        this.matiere = matiere;
        this.enseignant = enseignant;
        this.salle = salle;
        this.jour = jour;
        this.année = année;
        this.id = id;
    }

    public String getHeuredebut() { return heuredebut; }

    public void setHeuredebut(String heuredebut) { this.heuredebut = heuredebut; }

    public String getHeurefin() { return heurefin; }

    public void setHeurefin(String heurefin) { this.heurefin = heurefin; }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getAnnée() {
        return année;
    }

    public void setAnnée(String année) {
        this.année = année;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public static  final Comparator<Tabletime> BY_HORAIRE = new Comparator<Tabletime>() {
        @Override
        public int compare(Tabletime o1, Tabletime o2) {
            return o2.getHeurefin().compareTo(o1.getHeuredebut());
        }
    };


}
