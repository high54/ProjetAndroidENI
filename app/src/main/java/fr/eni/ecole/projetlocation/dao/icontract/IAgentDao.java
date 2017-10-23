package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class IAgentDao {
    public static final String TABLE_AGENTS = "agents";
    public static final String COLUMN_ID_AGENTS = "id";
    public static final String COLUMN_LOGIN_AGENTS = "login";
    public static final String COLUMN_PASS_AGENTS = "password";

    public static final String CREATE_TABLE_AGENTS= "create table "
            + TABLE_AGENTS + "(" + COLUMN_ID_AGENTS
            + " integer primary key autoincrement, " + COLUMN_LOGIN_AGENTS
            + " text not null, "+ COLUMN_PASS_AGENTS
            + " text not null"
            + ");  INSERT INTO "+ TABLE_AGENTS + " ("+COLUMN_LOGIN_AGENTS+","+COLUMN_PASS_AGENTS+") VALUES ('pass','pass');";
}
