package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class EDL {
    private int id;
    private String date;
    private ArrayList<Photo> photos;
    private LocationVehicule locationVehicule;

    public EDL() {
    }

    public EDL(int id, String date, ArrayList<Photo> photos, LocationVehicule locationVehicule) {
        this.id = id;
        this.date = date;
        this.photos = photos;
        this.locationVehicule = locationVehicule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public LocationVehicule getLocation() {
        return locationVehicule;
    }

    public void setLocation(LocationVehicule locationVehicule) {
        this.locationVehicule = locationVehicule;
    }

    @Override
    public String toString() {
        return "EDL{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", photos=" + photos +
                ", locationVehicule=" + locationVehicule +
                '}';
    }
}
