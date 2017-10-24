package fr.eni.ecole.projetlocation.models;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class Photo {
    private int id;
    private String date;
    private String uri;
    private EDL edl;
    private Vehicule vehicule;

    public Photo() {
    }

    public Photo(int id, String date, String uri, EDL edl) {
        this.id = id;
        this.date = date;
        this.uri = uri;
        this.edl = edl;
    }

    public Photo(int id, String date, String uri, Vehicule vehicule) {
        this.id = id;
        this.date = date;
        this.uri = uri;
        this.vehicule = vehicule;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public EDL getEdl() {
        return edl;
    }

    public void setEdl(EDL edl) {
        this.edl = edl;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", uri='" + uri + '\'' +
                ", edlId=" + edl +
                '}';
    }
}
