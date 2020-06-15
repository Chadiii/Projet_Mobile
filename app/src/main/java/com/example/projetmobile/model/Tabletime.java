package com.example.projetmobile.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class Tabletime {


    private String horaire, matiere, enseignant, salle, jour, année,id;


    public Tabletime(){

    }

    public Tabletime(String horaire, String matiere, String enseignant, String salle, String jour, String année, String id) {
        this.horaire = horaire;
        this.matiere = matiere;
        this.enseignant = enseignant;
        this.salle = salle;
        this.jour = jour;
        this.année = année;
        this.id = id;
    }


    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

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
            return o1.getHoraire().compareTo(o2.getHoraire());
        }
    };


}
