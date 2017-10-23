package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IAgenceDao {
    public static final String TABLE_AGENCE = "agence";
    public static final String COLUMN_NOM_AGENCE = "nom";
    public static final Integer NUM_COL_NOM = 0;

    public static final String CREATE_TABLE_AGENCE = " CREATE TABLE if not exists "
            + TABLE_AGENCE + "(" + COLUMN_NOM_AGENCE
            + " text not null); INSERT INTO "+TABLE_AGENCE+" ("+COLUMN_NOM_AGENCE+") VALUES ('Agence Bonjour');";
}
