package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IEDLDao {
    public static final String TABLE_EDLS = "etat_des_lieux";
    public static final String COLUMN_ID_EDLS = "id";
    public static final String COLUMN_DATE_EDLS= "date";
    public static final String COLUMN_ID_LOCATION_EDLS ="id_location";


    public static final String CREATE_TABLE_EDLS= "create table "
            + TABLE_EDLS + "(" + COLUMN_ID_EDLS
            + " integer primary key autoincrement, " + COLUMN_DATE_EDLS
            + " text not null,"+ COLUMN_ID_LOCATION_EDLS
            + " integer not null"
            + ");";
}

