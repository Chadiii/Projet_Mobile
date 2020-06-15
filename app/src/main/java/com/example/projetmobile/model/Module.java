package com.example.projetmobile.model;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Module implements Serializable {

    @Exclude private String id;


    private String semestre, code, intitule;
    private int volume;

    public Module() {

    }

    public Module(String semestre, String code, String intitule, int volume) {
        this.semestre = semestre;
        this.code = code;
        this.intitule = intitule;
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
