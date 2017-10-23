package fr.eni.ecole.projetlocation.models;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class ModelPhoto {
    private int id;
    private String date;
    private String uri;
    private ModelEDL edl;

    public ModelPhoto() {
    }

    public ModelPhoto(int id, String date, String uri, ModelEDL edl) {
        this.id = id;
        this.date = date;
        this.uri = uri;
        this.edl = edl;
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

    public ModelEDL getEdl() {
        return edl;
    }

    public void setEdl(ModelEDL edl) {
        this.edl = edl;
    }

    @Override
    public String toString() {
        return "ModelPhoto{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", uri='" + uri + '\'' +
                ", edlId=" + edl +
                '}';
    }
}
