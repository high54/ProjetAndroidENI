package fr.eni.ecole.projetlocation.models;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class Agence {
    private String nom;

    public Agence() {
    }

    public Agence(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Agence{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
