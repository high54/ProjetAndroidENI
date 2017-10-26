package fr.eni.ecole.projetlocation.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class Vehicule implements Parcelable {
    public static final String ESSENCE = "Essence";
    public static final String DIESEL = "Diesel";
    public static final String GPL = "GPL";
    public static final String ELECTRIQUE = "Electrique";

    public static final int TYPE_VILLE = 0;
    public static final int TYPE_HORS_VILLE = 1;

    private int id = -1;
    private int prix;
    private String immatriculation;
    private int type;
    private String marque;
    private String model;
    private String carburant;
    private Boolean loue = false;
    private ArrayList<Photo> photos;

    public Vehicule() {
    }

    public Vehicule(int id, int prix, String immatriculation, int type, String marque, String model, String carburant, Boolean loue) {
        this.id = id;
        this.prix = prix;
        this.immatriculation = immatriculation;
        this.type = type;
        this.marque = marque;
        this.model = model;
        this.carburant = carburant;
        this.loue = loue;
    }

    protected Vehicule(Parcel in) {
        id = in.readInt();
        prix = in.readInt();
        immatriculation = in.readString();
        type = in.readInt();
        marque = in.readString();
        model = in.readString();
        carburant = in.readString();
        loue =  in.readByte() != 0;
    }

    public static final Creator<Vehicule> CREATOR = new Creator<Vehicule>() {
        @Override
        public Vehicule createFromParcel(Parcel in) {
            return new Vehicule(in);
        }

        @Override
        public Vehicule[] newArray(int size) {
            return new Vehicule[size];
        }
    };

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

    public Boolean getLoue() {
        return loue;
    }

    public void setLoue(Boolean loue) {
        this.loue = loue;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public Vehicule addPhotos(Photo photo) {
        if (!this.hasPhoto(photo)) {
            this.photos.add(photo);
        }
        return this;
    }

    public Boolean hasPhoto(Photo photo) {
        return this.photos.contains(photo);
    }

    public Boolean removePhoto(Photo photo) {
        return this.photos.remove(photo);
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
                ", loué='" + loue + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(prix);
        parcel.writeString(immatriculation);
        parcel.writeInt(type);
        parcel.writeString(marque);
        parcel.writeString(model);
        parcel.writeString(carburant);
        parcel.writeByte((byte) (loue ? 1 : 0));
    }
}
