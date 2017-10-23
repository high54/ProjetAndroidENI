package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class ModelVehicule {
    private int id;
    private int prix;
    private String immatriculation;
    private String type;

    public ModelVehicule() {
    }

    public ModelVehicule(int id, int prix, String immatriculation, String type) {
        this.id = id;
        this.prix = prix;
        this.immatriculation = immatriculation;
        this.type = type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "ModelVehicule{" +
                "id=" + id +
                ", prix=" + prix +
                ", immatriculation='" + immatriculation + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
