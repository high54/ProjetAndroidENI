package fr.eni.ecole.projetlocation.dao.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.SQLiteHelper;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import fr.eni.ecole.projetlocation.models.Vehicule;

import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.COLUMN_DATE_DEPART_LOCATIONS;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.COLUMN_DATE_RETOUR_LOCATIONS;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.COLUMN_ID_CLIENT_LOCATIONS;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.COLUMN_ID_LOCATIONS;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.COLUMN_ID_VEHICULE_LOCATIONS;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.COLUMN_TARIF_LOCATIONS;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.NUM_COL_DATE_DEPART_LOCATION;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.NUM_COL_DATE_RETOUR_LOCATION;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.NUM_COL_ID_CLIENT_LOCATION;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.NUM_COL_ID_LOCATION;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.NUM_COL_ID_VEHICULE_LOCATION;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.NUM_COL_TARIF_LOCATION;
import static fr.eni.ecole.projetlocation.dao.location.ILocationContract.TABLE_LOCATIONS;

/**
 * Created by Administrateur on 24/10/2017.
 */
public class LocationDao {
    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase database;

    private String[] allColumns = {COLUMN_ID_LOCATIONS, COLUMN_ID_CLIENT_LOCATIONS,
            COLUMN_ID_VEHICULE_LOCATIONS, COLUMN_DATE_DEPART_LOCATIONS,
            COLUMN_DATE_RETOUR_LOCATIONS, COLUMN_TARIF_LOCATIONS};

    public LocationDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        database = sqLiteHelper.getWritableDatabase();

    }

    /**
     * Ajoute une locationVehicule
     * Prend en paramètre un objet locationVehicule
     *
     * @param locationVehicule
     * @return locationVehicule
     * Retourne un objet locationVehicule avec l'ID
     */
    public LocationVehicule insertLocation(LocationVehicule locationVehicule) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_CLIENT_LOCATIONS, locationVehicule.getClient().getId());
        values.put(COLUMN_ID_VEHICULE_LOCATIONS, locationVehicule.getVehicule().getId());
        values.put(COLUMN_DATE_DEPART_LOCATIONS, locationVehicule.getDepart());
        values.put(COLUMN_TARIF_LOCATIONS, locationVehicule.getTarif());

        long insertId = database.insert(TABLE_LOCATIONS, null,
                values);
        Cursor cursor = database.query(TABLE_LOCATIONS,
                allColumns, COLUMN_ID_LOCATIONS + " = " + insertId, null,
                null, null, null);
        LocationVehicule newLocationVehicule = new LocationVehicule();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            newLocationVehicule = mappage(cursor);
        }
        cursor.close();

        return newLocationVehicule;
    }

    /**
     * Met à jour un locationVehicule
     * Prends en paramètre un objet locationVehicule
     *
     * @param locationVehicule
     * @return locationVehicule
     * Retourne un objet locationVehicule
     */
    public LocationVehicule updateLocation(LocationVehicule locationVehicule) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_CLIENT_LOCATIONS, locationVehicule.getClient().getId());
        values.put(COLUMN_ID_VEHICULE_LOCATIONS, locationVehicule.getVehicule().getId());
        values.put(COLUMN_DATE_DEPART_LOCATIONS, locationVehicule.getDepart());
        values.put(COLUMN_DATE_RETOUR_LOCATIONS, locationVehicule.getRetour());
        values.put(COLUMN_TARIF_LOCATIONS, locationVehicule.getTarif());


        database.update(TABLE_LOCATIONS, values, "id=" + locationVehicule.getId(), null);

        return locationVehicule;
    }

    /**
     * Supprime un locationVehicule
     * Prends en paramètre un objet locationVehicule
     *
     * @param locationVehicule
     */
    public void deleteLocation(LocationVehicule locationVehicule) {
        database.delete(TABLE_LOCATIONS, COLUMN_ID_LOCATIONS
                + " = " + locationVehicule.getId(), null);
    }

    public List<LocationVehicule> getLocationByClient(LocationVehicule locationVehicule) {

        List<LocationVehicule> locationVehicules = new ArrayList<LocationVehicule>();

        Cursor cursor = database.query(TABLE_LOCATIONS,
                allColumns, " id_client = '" + locationVehicule.getClient().getId() + "'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationVehicule newlocation = mappage(cursor);
            locationVehicules.add(newlocation);
            cursor.moveToNext();
        }
        cursor.close();
        return locationVehicules;
    }

    /**
     * Retourne une liste de locationVehicule pour une voiture
     *
     * @return clients
     */
    public List<LocationVehicule> getAllLocation(int id) {

        List<LocationVehicule> locationVehicules = new ArrayList<LocationVehicule>();

        Cursor cursor = database.query(TABLE_LOCATIONS,
                allColumns, " id_vehicule = '" + id + "'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationVehicule newlocation = mappage(cursor);
            locationVehicules.add(newlocation);
            cursor.moveToNext();
        }
        cursor.close();
        return locationVehicules;
    }

    public LocationVehicule getLastLocation(int id) {
        Cursor cursor = database.query(TABLE_LOCATIONS,allColumns,COLUMN_ID_VEHICULE_LOCATIONS+"='"+id+"'"+" AND "+COLUMN_DATE_RETOUR_LOCATIONS + "= '' OR "+COLUMN_DATE_RETOUR_LOCATIONS+ " is null",null,null,null,null);
        cursor.moveToLast();
        LocationVehicule locationVehicule = mappage(cursor);
        return locationVehicule;
    }

    public List<LocationVehicule> getLocationsForStats() {

        List<LocationVehicule> locationVehicules = new ArrayList<LocationVehicule>();

        Cursor cursor = database.query(TABLE_LOCATIONS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationVehicule newlocation = mappage(cursor);
            locationVehicules.add(newlocation);
            cursor.moveToNext();
        }
        cursor.close();
        return locationVehicules;
    }

    /**
     * Retourne un locationVehicule en fonction de son ID
     *
     * @param locationVehicule
     * @return
     */
    public LocationVehicule getLocationById(LocationVehicule locationVehicule) {
        Cursor cursor = database.query(TABLE_LOCATIONS,
                allColumns, " id = " + locationVehicule.getId(), null, null, null, null);
        cursor.moveToFirst();
        locationVehicule = mappage(cursor);
        return locationVehicule;
    }

    public LocationVehicule mappage(Cursor cursor) {
        Client client = new Client();
        Vehicule vehicule = new Vehicule();
        LocationVehicule locationVehicule = new LocationVehicule();

        locationVehicule.setId(cursor.getInt(NUM_COL_ID_LOCATION));
        client.setId(cursor.getInt(NUM_COL_ID_CLIENT_LOCATION));
        locationVehicule.setClient(client);
        vehicule.setId(cursor.getInt(NUM_COL_ID_VEHICULE_LOCATION));
        locationVehicule.setVehicule(vehicule);
        locationVehicule.setDepart(cursor.getString(NUM_COL_DATE_DEPART_LOCATION));
        locationVehicule.setRetour(cursor.getString(NUM_COL_DATE_RETOUR_LOCATION));
        locationVehicule.setTarif(cursor.getInt(NUM_COL_TARIF_LOCATION));

        return locationVehicule;
    }
}
