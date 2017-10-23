package fr.eni.ecole.projetlocation.models;

import java.util.Date;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class ModelClient {
    private int id;
    private String nom;
    private String prenom;
    private int telephone;
    private String adresse;
    private int codePostal;
    private String ville;
    private Date dateNaissance;


    public ModelClient() {
    }

    public ModelClient(int id, String nom, String prenom, int telephone, String adresse, int codePostal, String ville, Date dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.dateNaissance = dateNaissance;
    }

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

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "ModelClient{" +
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
}
