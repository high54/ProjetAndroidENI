package fr.eni.ecole.projetlocation.dao.client;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.SQLiteHelper;
import fr.eni.ecole.projetlocation.models.ModelClient;

import static fr.eni.ecole.projetlocation.dao.client.IContract.*;


/**
 * Created by Administrateur on 23/10/2017.
 */
public class ClientDao {

    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase database;

    private String[] allColumns = {COLUMN_ID_CLIENTS, COLUMN_NOM_CLIENTS,
            COLUMN_PRENOM_CLIENTS, COLUMN_TELEPHONE_CLIENTS,
            COLUMN_ADRESSE_CLIENTS, COLUMN_CODE_POSTAL_CLIENTS, COLUMN_VILLE_CLIENT,
            COLUMN_DATE_NAISSANCE_CLIENT};

    public ClientDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }


    public void open() throws SQLException {
        database = sqLiteHelper.getWritableDatabase();
    }


    public void close() {
        sqLiteHelper.close();
    }

    /**
     * Ajoute un client
     * Prend en paramètre un objet client
     *
     * @param client
     * @return client
     * Retourne un objet client avec l'ID
     */
    public ModelClient insertClient(ModelClient client) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM_CLIENTS, client.getNom());
        values.put(COLUMN_PRENOM_CLIENTS, client.getPrenom());
        values.put(COLUMN_TELEPHONE_CLIENTS, client.getTelephone());
        values.put(COLUMN_ADRESSE_CLIENTS, client.getAdresse());
        values.put(COLUMN_CODE_POSTAL_CLIENTS, client.getCodePostal());
        values.put(COLUMN_VILLE_CLIENT, client.getVille());
        values.put(COLUMN_DATE_NAISSANCE_CLIENT, client.getDateNaissance().toString());


        long insertId = database.insert(TABLE_CLIENTS, null,
                values);
        Cursor cursor = database.query(TABLE_CLIENTS,
                allColumns, COLUMN_ID_CLIENTS + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ModelClient newClient = mappage(cursor);
        cursor.close();

        return newClient;
    }

    /**
     * Met à jour un client
     * Prends en paramètre un objet client
     *
     * @param client
     * @return client
     * Retourne un objet client
     */
    public ModelClient updateClient(ModelClient client) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM_CLIENTS, client.getNom());
        values.put(COLUMN_PRENOM_CLIENTS, client.getPrenom());
        values.put(COLUMN_TELEPHONE_CLIENTS, client.getTelephone());
        values.put(COLUMN_ADRESSE_CLIENTS, client.getAdresse());
        values.put(COLUMN_CODE_POSTAL_CLIENTS, client.getCodePostal());
        values.put(COLUMN_VILLE_CLIENT, client.getVille());
        values.put(COLUMN_DATE_NAISSANCE_CLIENT, client.getDateNaissance().toString());

        database.update(TABLE_CLIENTS, values, "id=" + client.getId(), null);

        return client;
    }

    /**
     * Supprime un client
     * Prends en paramètre un objet client
     *
     * @param client
     */
    public void deleteClient(ModelClient client) {
        database.delete(TABLE_CLIENTS, COLUMN_ID_CLIENTS
                + " = " + client.getId(), null);
    }

    /**
     * Retourne une liste de client
     *
     * @return List<ModelClient> clients
     */
    public List<ModelClient> getClients() {

        List<ModelClient> clients = new ArrayList<ModelClient>();

        Cursor cursor = database.query(TABLE_CLIENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelClient client = mappage(cursor);
            clients.add(client);
            cursor.moveToNext();
        }
        cursor.close();
        return clients;
    }

    /**
     * Retourne une liste de client
     *
     * @param nom
     * @param prenom
     * @return clients
     */
    public List<ModelClient> getClientsByNomPrenom(String nom, String prenom) {

        List<ModelClient> clients = new ArrayList<ModelClient>();

        Cursor cursor = database.query(TABLE_CLIENTS,
                allColumns, " nom =" + nom + " AND prenom =" + prenom, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ModelClient client = mappage(cursor);
            clients.add(client);
            cursor.moveToNext();
        }
        cursor.close();
        return clients;
    }

    /**
     * Retourne un client en fonction de son ID
     * @param client
     * @return
     */
    public ModelClient getClientsById(ModelClient client) {


        Cursor cursor = database.query(TABLE_CLIENTS,
                allColumns, " id = " + client.getId(), null, null, null, null);

        client = mappage(cursor);
        return client;
    }

    public ModelClient mappage(Cursor cursor) {
        ModelClient client = new ModelClient();
        client.setId(cursor.getInt(NUM_COL_ID_CLIENTS));
        client.setNom(cursor.getString(NUM_COL_NOM_CLIENTS));
        client.setPrenom(cursor.getString(NUM_COL_PRENOM_CLIENTS));
        client.setAdresse(cursor.getString(NUM_COL_ADRESSE_CLIENTS));
        client.setCodePostal(cursor.getInt(NUM_COL_CODE_POSTAL_CLIENTS));
        client.setVille(cursor.getString(NUM_COL_VILLE_CLIENTS));
        client.setTelephone(cursor.getInt(NUM_COL_TELEPHONE_CLIENTS));
        String dateNaissance = cursor.getString(NUM_COL_DATE_NAISSANCE_CLIENTS);
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            client.setDateNaissance(date.parse(dateNaissance));
        } catch (ParseException e) {
            Log.e("WTF", "Parsing ISO8601 datetime failed", e);
        }
        return client;
    }

}
