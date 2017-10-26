package fr.eni.ecole.projetlocation.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class Client implements Parcelable{
    private int id;
    private String nom;
    private String prenom;
    private float telephone;
    private String adresse;
    private int codePostal;
    private String ville;
    private String dateNaissance;


    public Client() {
    }

    public Client(String nom, String prenom, int telephone, String adresse, int codePostal, String ville, String dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.dateNaissance = dateNaissance;
    }

    protected Client(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        prenom = in.readString();
        telephone = in.readFloat();
        adresse = in.readString();
        codePostal = in.readInt();
        ville = in.readString();
        dateNaissance = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public float getTelephone() {
        return telephone;
    }

    public void setTelephone(float telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone=" + telephone +
                ", adresse='" + adresse + '\'' +
                ", codePostal=" + codePostal +
                ", ville='" + ville + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nom);
        parcel.writeString(prenom);
        parcel.writeFloat(telephone);
        parcel.writeString(adresse);
        parcel.writeInt(codePostal);
        parcel.writeString(ville);
        parcel.writeString(dateNaissance);
    }
}
