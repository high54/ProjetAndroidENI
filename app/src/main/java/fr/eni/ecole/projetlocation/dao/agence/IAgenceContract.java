package fr.eni.ecole.projetlocation.dao.agence;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IAgenceContract {
    public static final String TABLE_AGENCE = "agence";
    public static final String COLUMN_NOM_AGENCE = "nom";
    public static final Integer NUM_COL_NOM = 0;

    public static final String CREATE_TABLE_AGENCE = " CREATE TABLE "
            + TABLE_AGENCE + "(" + COLUMN_NOM_AGENCE
            + " text not null);";

    public static final String INSERT_AGENCE = "INSERT INTO "+TABLE_AGENCE +" ("+COLUMN_NOM_AGENCE+") values ('Agence Bonjour');";
}
