package lags;

import java.util.*;

public class Ordre {

    public Ordre(String id, int debut, int duree, double prix) {
        this.id = id;
        this.debut = debut; // au format AAAAJJJ par exemple 25 février 2015 = 2015056
        this.duree = duree;
        this.prix = prix;
    }

    //id de l'ordre
    private String id;

    // debut
    private int debut;

    // duree
    private int duree;

    // valeur
    private double prix;

    // getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}