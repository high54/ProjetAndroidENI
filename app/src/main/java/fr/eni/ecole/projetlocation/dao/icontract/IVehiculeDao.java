package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IVehiculeDao {
    public static final String TABLE_VEHICULES = "vehicules";
    public static final String COLUMN_ID_VEHICULES = "id";
    public static final String COLUMN_PRIX_VEHICULES = "prix";
    public static final String COLUMN_IMMATRICULATION_VEHICULES = "immatriculation";
    public static final String COLUMN_TYPE_VEHICULES = "type";


    public static final String CREATE_TABLE_VEHICULES= "create table "
            + TABLE_VEHICULES + "(" + COLUMN_ID_VEHICULES
            + " integer primary key autoincrement, " + COLUMN_IMMATRICULATION_VEHICULES
            + " text not null, "+ COLUMN_TYPE_VEHICULES
            + " text not null, "+ COLUMN_PRIX_VEHICULES
            + " integer not null"
            + ");";
}
