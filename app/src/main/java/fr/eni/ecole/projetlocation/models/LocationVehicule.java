package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class LocationVehicule {
    private int id;
    private Client client;
    private String depart;
    private String retour;
    private Vehicule vehicule;
    private ArrayList<EDL> edls;


    public LocationVehicule() {
    }

    public LocationVehicule(int id, Client client, String depart, String retour, Vehicule vehicule, ArrayList<EDL> edls) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
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

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public ArrayList<EDL> getEdls() {
        return edls;
    }

    public void setEdls(ArrayList<EDL> edls) {
        this.edls = edls;
    }

    @Override
    public String toString() {
        return "LocationVehicule{" +
                "id=" + id +
                ", client=" + client +
                ", depart='" + depart + '\'' +
                ", retour='" + retour + '\'' +
                ", vehicule=" + vehicule +
                ", edls=" + edls +
                '}';
    }
}
