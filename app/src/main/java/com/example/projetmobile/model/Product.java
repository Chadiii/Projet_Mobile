package com.example.projetmobile.model;
import com.google.firebase.firestore.Exclude;

public class Product {


    @Exclude
    private String id;

    private String semestre, code, intitule;
    private int volume;

    public Product() {

    }

    public Product(String semestre, String code, String intitule, int volume) {
        this.semestre = semestre;
        this.code = code;
        this.intitule = intitule;
        this.volume = volume;
    }


    public void setId(String id) {this.id = id; }

    public String getSemestre() {
        return semestre;
    }

    public String getCode() {
        return code;
    }

    public String getIntitule() {
        return intitule;
    }


    public int getVolume() {
        return volume;
    }

}
