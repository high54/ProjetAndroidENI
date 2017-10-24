package fr.eni.ecole.projetlocation.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.eni.ecole.projetlocation.dao.icontract.IAgenceDao;
import fr.eni.ecole.projetlocation.dao.icontract.IAgentDao;
import fr.eni.ecole.projetlocation.dao.client.IClientContract;
import fr.eni.ecole.projetlocation.dao.edl.IEDLContract;
import fr.eni.ecole.projetlocation.dao.location.ILocationContract;
import fr.eni.ecole.projetlocation.dao.icontract.IPhotoDao;
import fr.eni.ecole.projetlocation.dao.vehicule.IVehiculeContract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "location.db";
    private static final int DATABASE_VERSION = 5;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(IAgenceDao.CREATE_TABLE_AGENCE);
        sqLiteDatabase.execSQL(IAgentDao.CREATE_TABLE_AGENTS);
        sqLiteDatabase.execSQL(IClientContract.CREATE_TABLE_CLIENTS);
        sqLiteDatabase.execSQL(IEDLContract.CREATE_TABLE_EDLS);
        sqLiteDatabase.execSQL(ILocationContract.CREATE_TABLE_LOCATIONS);
        sqLiteDatabase.execSQL(IPhotoDao.CREATE_TABLE_PHOTOS);
        sqLiteDatabase.execSQL(IVehiculeContract.CREATE_TABLE_VEHICULES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IAgenceDao.TABLE_AGENCE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IAgentDao.TABLE_AGENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IClientContract.TABLE_CLIENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IEDLContract.TABLE_EDLS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ILocationContract.TABLE_LOCATIONS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IPhotoDao.TABLE_PHOTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IVehiculeContract.TABLE_VEHICULES);
        onCreate(sqLiteDatabase);
    }
}
