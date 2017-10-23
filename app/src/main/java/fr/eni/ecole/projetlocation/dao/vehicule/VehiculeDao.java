package fr.eni.ecole.projetlocation.dao.vehicule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fr.eni.ecole.projetlocation.dao.SQLiteHelper;
import fr.eni.ecole.projetlocation.models.Vehicule;

import static fr.eni.ecole.projetlocation.dao.vehicule.IVehiculeContract.*;

/**
 * Created by bkrafft2016 on 23/10/2017.
 */
public class VehiculeDao {

    private static final String TAG = "VehiculeDAO" ;
    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase sqLiteDatabase;

    private String[] allColumns = {COLUMN_ID_VEHICULES, COLUMN_IMMATRICULATION_VEHICULES,
            COLUMN_CARBURANT_VEHICULES, COLUMN_MARQUE_VEHICULES, COLUMN_MODELE_VEHICULES,
            COLUMN_PRIX_VEHICULES, COLUMN_TYPE_VEHICULES};

    public VehiculeDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public long createVehicule(Vehicule vehicule) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMMATRICULATION_VEHICULES, vehicule.getImmatriculation());
        values.put(COLUMN_CARBURANT_VEHICULES, vehicule.getCarburant());
        values.put(COLUMN_MARQUE_VEHICULES, vehicule.getMarque());
        values.put(COLUMN_MODELE_VEHICULES, vehicule.getModel());
        values.put(COLUMN_PRIX_VEHICULES, vehicule.getPrix());
        values.put(COLUMN_TYPE_VEHICULES, vehicule.getType());
        values.put(COLUMN_LOUE_VEHICULES, vehicule.getLoue());
        Log.wtf(TAG, "insert");
        return sqLiteDatabase.insert(TABLE_VEHICULES, null, values);
    }

    public Cursor selectAllCursor() {
        return sqLiteDatabase.query(TABLE_VEHICULES, allColumns, null, null, null, null, null);
    }

    public ArrayList<Vehicule> selectAll() {
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        Log.wtf(TAG, "selectAll");
        Cursor c = selectAllCursor();
        while(c.moveToNext()) {
            vehicules.add(map(c));
            Log.wtf(TAG, "vehicule");
        }

        return vehicules;
    }

    public ArrayList<Vehicule> selectAllRent(Boolean bool) {
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        Log.wtf(TAG, "selectAllRent "+bool);
        Cursor c = sqLiteDatabase.query(TABLE_VEHICULES, allColumns, COLUMN_LOUE_VEHICULES + "=" + bool, null, null, null, null);
        while(c.moveToNext()) {
            vehicules.add(map(c));
        }

        return vehicules;
    }

    public Vehicule selectById(int id) {
        Vehicule vehicule = new Vehicule();
        Log.wtf(TAG, "selectByid");
        Cursor c = sqLiteDatabase.query(TABLE_VEHICULES, null, COLUMN_ID_VEHICULES + "=" + id, null, null, null, null);
        if(c.getCount() > 0){
            vehicule = map(c);
        }
        return vehicule;
    }

    public boolean update(Vehicule vehicule) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMMATRICULATION_VEHICULES, vehicule.getImmatriculation());
        values.put(COLUMN_CARBURANT_VEHICULES, vehicule.getCarburant());
        values.put(COLUMN_MARQUE_VEHICULES, vehicule.getMarque());
        values.put(COLUMN_MODELE_VEHICULES, vehicule.getModel());
        values.put(COLUMN_PRIX_VEHICULES, vehicule.getPrix());
        values.put(COLUMN_TYPE_VEHICULES, vehicule.getType());
        values.put(COLUMN_LOUE_VEHICULES, vehicule.getLoue());
        Log.wtf(TAG, "update");
        return sqLiteDatabase.update(TABLE_VEHICULES, values, COLUMN_ID_VEHICULES + " = " + vehicule.getId(), null) > 0;
    }

    public Vehicule map(Cursor c) {
        Vehicule vehicule = new Vehicule();
        vehicule.setPrix(c.getInt(NUM_COLUMN_PRIX_VEHICULES));
        vehicule.setImmatriculation(c.getString(NUM_COLUMN_IMMATRICULATION_VEHICULES));
        vehicule.setType(c.getInt(NUM_COLUMN_TYPE_VEHICULES));
        vehicule.setMarque(c.getString(NUM_COLUMN_MARQUE_VEHICULES));
        vehicule.setModel(c.getString(NUM_COLUMN_MODELE_VEHICULES));
        vehicule.setCarburant(c.getString(NUM_COLUMN_CARBURANT_VEHICULES));
        vehicule.setLoue((c.getInt(NUM_COLUMN_LOUE_VEHICULES) > 0));

        vehicule.setId(c.getInt(NUM_COLUMN_ID_VEHICULES));
        Log.wtf(TAG, vehicule.toString());
        return vehicule;

    }
}
