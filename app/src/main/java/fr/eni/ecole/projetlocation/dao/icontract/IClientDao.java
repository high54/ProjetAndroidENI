package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IClientDao {
    public static final String TABLE_CLIENTS = "clients";
    public static final String COLUMN_ID_CLIENTS = "id";
    public static final String COLUMN_NOM_CLIENTS = "nom";
    public static final String COLUMN_PRENOM_CLIENTS = "prenom";
    public static final String COLUMN_TELEPHONE_CLIENTS = "telephone";
    public static final String COLUMN_ADRESSE_CLIENTS = "adresse";
    public static final String COLUMN_CODE_POSTAL_CLIENTS = "codePostal";
    public static final String COLUMN_VILLE_CLIENT = "ville";

    public static final String CREATE_TABLE_CLIENTS= "create table "
            + TABLE_CLIENTS + "(" + COLUMN_ID_CLIENTS
            + " integer primary key autoincrement, " + COLUMN_NOM_CLIENTS
            + " text not null, "+ COLUMN_PRENOM_CLIENTS
            + " text not null, "+ COLUMN_TELEPHONE_CLIENTS
            + " text not null, "+ COLUMN_ADRESSE_CLIENTS
            + " text not null, "+ COLUMN_CODE_POSTAL_CLIENTS
            + " text not null, "+ COLUMN_VILLE_CLIENT
            + " text not null"
            + ");";

}
