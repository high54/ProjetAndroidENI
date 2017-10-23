package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class EDL {
    private int id;
    private String date;
    private ArrayList<Photo> photos;
    private Location location;

    public EDL() {
    }

    public EDL(int id, String date, ArrayList<Photo> photos, Location location) {
        this.id = id;
        this.date = date;
        this.photos = photos;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "EDL{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", photos=" + photos +
                ", location=" + location +
                '}';
    }
}
