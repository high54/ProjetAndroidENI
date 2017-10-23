package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class ModelLocation {
    private int id;
    private ModelClient client;
    private String depart;
    private String retour;
    private ModelVehicule vehicule;
    private ArrayList<ModelEDL> edls;


    public ModelLocation() {
    }

    public ModelLocation(int id, ModelClient client, String depart, String retour, ModelVehicule vehicule, ArrayList<ModelEDL> edls) {
        this.id = id;
        this.client = client;
        this.depart = depart;
        this.retour = retour;
        this.vehicule = vehicule;
        this.edls = edls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelClient getClient() {
        return client;
    }

    public void setClient(ModelClient client) {
        this.client = client;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getRetour() {
        return retour;
    }

    public void setRetour(String retour) {
        this.retour = retour;
    }

    public ModelVehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(ModelVehicule vehicule) {
        this.vehicule = vehicule;
    }

    public ArrayList<ModelEDL> getEdls() {
        return edls;
    }

    public void setEdls(ArrayList<ModelEDL> edls) {
        this.edls = edls;
    }

    @Override
    public String toString() {
        return "ModelLocation{" +
                "id=" + id +
                ", client=" + client +
                ", depart='" + depart + '\'' +
                ", retour='" + retour + '\'' +
                ", vehicule=" + vehicule +
                ", edls=" + edls +
                '}';
    }
}
