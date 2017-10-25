package fr.eni.ecole.projetlocation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class LocationVehicule implements Parcelable{
    private int id;
    private Client client;
    private String depart;
    private String retour;
    private Vehicule vehicule;
    private ArrayList<EDL> edls;
    private int tarif;


    public LocationVehicule() {
    }

    public LocationVehicule(int id, Client client, String depart, String retour, Vehicule vehicule, ArrayList<EDL> edls, int tarif) {
        this.id = id;
        this.client = client;
        this.depart = depart;
        this.retour = retour;
        this.vehicule = vehicule;
        this.edls = edls;
        this.tarif = tarif;
    }


    protected LocationVehicule(Parcel in) {
        id = in.readInt();
        client = in.readParcelable(Client.class.getClassLoader());
        depart = in.readString();
        retour = in.readString();
        vehicule = in.readParcelable(Vehicule.class.getClassLoader());
        tarif = in.readInt();
    }

    public static final Creator<LocationVehicule> CREATOR = new Creator<LocationVehicule>() {
        @Override
        public LocationVehicule createFromParcel(Parcel in) {
            return new LocationVehicule(in);
        }

        @Override
        public LocationVehicule[] newArray(int size) {
            return new LocationVehicule[size];
        }
    };

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

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(client, i);
        parcel.writeString(depart);
        parcel.writeString(retour);
        parcel.writeParcelable(vehicule, i);
        parcel.writeInt(tarif);
    }
}
