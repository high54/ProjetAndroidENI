package fr.eni.ecole.projetlocation.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import fr.eni.ecole.projetlocation.dao.icontract.IAgenceDao;
import fr.eni.ecole.projetlocation.models.ModelAgence;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class AgenceDao {

    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase sqLiteDatabase;

    private String[] allColumns = {IAgenceDao.COLUMN_NOM_AGENCE};

    public AgenceDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }


    public void open() throws SQLException {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }


    public void close() {
        sqLiteHelper.close();
    }

    public ModelAgence updateAgence(ModelAgence agence){
        ContentValues values = new ContentValues();
        values.put(IAgenceDao.COLUMN_NOM_AGENCE, agence.getNom());
        sqLiteDatabase.update(IAgenceDao.TABLE_AGENCE,values,null,null);
        return agence;
    }
    public ModelAgence getAgence() {
       ModelAgence agence = new ModelAgence();

        Cursor cursor = sqLiteDatabase.query(IAgenceDao.TABLE_AGENCE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            agence= mappage(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return agence;
    }

    public ModelAgence mappage(Cursor cursor){
       ModelAgence agence = new ModelAgence();

        agence.setNom(cursor.getString(IAgenceDao.NUM_COL_NOM));
        return agence;
    }





}
