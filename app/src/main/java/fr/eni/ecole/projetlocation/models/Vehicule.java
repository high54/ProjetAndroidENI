package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class Vehicule {
    private int id;
    private int prix;
    private String immatriculation;
    private int type;
    private String marque;
    private String model;
    private String carburant;


    public Vehicule() {
    }

    public Vehicule(int id, int prix, String immatriculation, int type, String marque, String model, String carburant) {
        this.id = id;
        this.prix = prix;
        this.immatriculation = immatriculation;
        this.type = type;
        this.marque = marque;
        this.model = model;
        this.carburant = carburant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", prix=" + prix +
                ", immatriculation='" + immatriculation + '\'' +
                ", type=" + type +
                ", marque='" + marque + '\'' +
                ", model='" + model + '\'' +
                ", carburant='" + carburant + '\'' +
                '}';
    }
}
