package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IPhotoDao {
    public static final String TABLE_PHOTOS = "photos";
    public static final String COLUMN_ID_PHOTOS = "id";
    public static final String COLUMN_DATE_PHOTOS = "date";
    public static final String COLUMN_URI_PHOTOS = "uri";
    public static final String COLUMN_ID_EDL_PHOTOS = "id_edl";


    public static final String CREATE_TABLE_PHOTOS= "create table "
            + TABLE_PHOTOS + "(" + COLUMN_ID_PHOTOS
            + " integer primary key autoincrement, " + COLUMN_DATE_PHOTOS
            + " text not null, "+ COLUMN_URI_PHOTOS
            + " text not null, "+ COLUMN_ID_EDL_PHOTOS
            + " integer not null"
            + ");";
}
