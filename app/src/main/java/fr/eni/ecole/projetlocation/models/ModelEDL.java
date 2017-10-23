package fr.eni.ecole.projetlocation.models;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class ModelEDL {
    private int id;
    private String date;
    private ArrayList<ModelPhoto> photos;
    private ModelLocation location;

    public ModelEDL() {
    }

    public ModelEDL(int id, String date, ArrayList<ModelPhoto> photos, ModelLocation location) {
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

    public ArrayList<ModelPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<ModelPhoto> photos) {
        this.photos = photos;
    }

    public ModelLocation getLocation() {
        return location;
    }

    public void setLocation(ModelLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ModelEDL{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", photos=" + photos +
                ", location=" + location +
                '}';
    }
}
