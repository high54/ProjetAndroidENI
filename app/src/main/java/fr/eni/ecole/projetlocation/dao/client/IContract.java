package fr.eni.ecole.projetlocation.dao.client;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IContract {
    public static final String TABLE_CLIENTS = "clients";
    public static final String COLUMN_ID_CLIENTS = "id";
    public static final String COLUMN_NOM_CLIENTS = "nom";
    public static final String COLUMN_PRENOM_CLIENTS = "prenom";
    public static final String COLUMN_TELEPHONE_CLIENTS = "telephone";
    public static final String COLUMN_ADRESSE_CLIENTS = "adresse";
    public static final String COLUMN_CODE_POSTAL_CLIENTS = "codePostal";
    public static final String COLUMN_VILLE_CLIENT = "ville";
    public static final String COLUMN_DATE_NAISSANCE_CLIENT = "date_naissance";

    public static final int NUM_COL_ID_CLIENTS = 0;
    public static final int NUM_COL_NOM_CLIENTS = 1;
    public static final int NUM_COL_PRENOM_CLIENTS = 2;
    public static final int NUM_COL_TELEPHONE_CLIENTS = 3;
    public static final int NUM_COL_ADRESSE_CLIENTS = 4;
    public static final int NUM_COL_CODE_POSTAL_CLIENTS = 5;
    public static final int NUM_COL_VILLE_CLIENTS = 6;
    public static final int NUM_COL_DATE_NAISSANCE_CLIENTS = 7;




    public static final String CREATE_TABLE_CLIENTS= "create table "
            + TABLE_CLIENTS + "(" + COLUMN_ID_CLIENTS
            + " integer primary key autoincrement, " + COLUMN_NOM_CLIENTS
            + " text not null, "+ COLUMN_PRENOM_CLIENTS
            + " text not null, "+ COLUMN_TELEPHONE_CLIENTS
            + " integer not null, "+ COLUMN_ADRESSE_CLIENTS
            + " text, "+ COLUMN_CODE_POSTAL_CLIENTS
            + " integer, "+ COLUMN_VILLE_CLIENT
            + " text, "+ COLUMN_DATE_NAISSANCE_CLIENT
            +" date not null "
            + ");";

}
