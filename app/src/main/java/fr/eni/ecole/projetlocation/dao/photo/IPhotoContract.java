package fr.eni.ecole.projetlocation.dao.photo;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IPhotoContract {
    public static final String TABLE_PHOTOS = "photos";
    public static final String COLUMN_ID_PHOTOS = "id";
    public static final String COLUMN_DATE_PHOTOS = "date";
    public static final String COLUMN_URI_PHOTOS = "uri";
    public static final String COLUMN_ID_EDL_PHOTOS = "id_edl";
    public static final String COLUMN_ID_VEHICULE_PHOTOS = "id_vehicule";

    public static final int NUM_COL_ID_VEHICULE =4;
    public static final int NUM_COL_ID_EDL = 1;
    public static final int NUM_COL_ID_PHOTO = 0;
    public static final int NUM_COL_DATE_PHOTO = 2;
    public static final int NUM_COL_URI_PHOTO = 3;



    public static final String CREATE_TABLE_PHOTOS= "create table "
            + TABLE_PHOTOS + "(" + COLUMN_ID_PHOTOS
            + " integer primary key autoincrement, " + COLUMN_DATE_PHOTOS
            + " text not null, "+ COLUMN_URI_PHOTOS
            + " text not null, "+ COLUMN_ID_EDL_PHOTOS
            + " integer, " + COLUMN_ID_VEHICULE_PHOTOS
            + " integer "
            + ");";
}
