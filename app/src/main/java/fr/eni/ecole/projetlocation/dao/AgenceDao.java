package fr.eni.ecole.projetlocation.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.eni.ecole.projetlocation.dao.icontract.IAgenceDao;
import fr.eni.ecole.projetlocation.models.Agence;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class AgenceDao {

    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase sqLiteDatabase;

    private String[] allColumns = {IAgenceDao.COLUMN_NOM_AGENCE};

    public AgenceDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public Agence updateAgence(Agence agence) {
        ContentValues values = new ContentValues();
        values.put(IAgenceDao.COLUMN_NOM_AGENCE, agence.getNom());
        sqLiteDatabase.update(IAgenceDao.TABLE_AGENCE, values, null, null);
        return agence;
    }

    public Agence getAgence() {
        Agence agence = new Agence();

        Cursor cursor = sqLiteDatabase.query(IAgenceDao.TABLE_AGENCE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            agence = mappage(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return agence;
    }

    public Agence mappage(Cursor cursor) {
        Agence agence = new Agence();

        agence.setNom(cursor.getString(IAgenceDao.NUM_COL_NOM));
        return agence;
    }

}
