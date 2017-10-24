package fr.eni.ecole.projetlocation.dao.edl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.SQLiteHelper;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import static fr.eni.ecole.projetlocation.dao.edl.IEDLContract.*;
/**
 * Created by Administrateur on 24/10/2017.
 */
public class EDLDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase database;

    private String[] allColumns = {COLUMN_ID_EDLS, COLUMN_ID_LOCATION_EDLS,
            COLUMN_DATE_EDLS};

    public EDLDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }


    public void open() throws SQLException {
        database = sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteHelper.close();
    }

    public EDL insertEDL(EDL edl){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_LOCATION_EDLS, edl.getLocation().getId());
        values.put(COLUMN_DATE_EDLS, edl.getDate());
        long insertId = database.insert(TABLE_EDLS, null,
                values);
        Cursor cursor = database.query(TABLE_EDLS,
                allColumns, COLUMN_ID_EDLS + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        EDL newEDl = map(cursor);
        cursor.close();
        return newEDl;
    }

    public List<EDL> getEdlByLocation(int id){
        List<EDL> edls = new ArrayList<EDL>();

        Cursor cursor = database.query(TABLE_EDLS,
                allColumns, " id_location = '" + id +"'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EDL edl = map(cursor);
            edls.add(edl);
            cursor.moveToNext();
        }
        cursor.close();
        return edls;
    }

    public EDL map(Cursor c){
        EDL edl = new EDL();
        LocationVehicule location = new LocationVehicule();
        edl.setId(c.getInt(NUM_COL_ID_EDL));
        location.setId(c.getInt(NUM_ID_LOCATION_EDL));
        edl.setLocation(location);
        edl.setDate(c.getString(NUM_COL_DATE_EDL));
        return edl;
    }





}
