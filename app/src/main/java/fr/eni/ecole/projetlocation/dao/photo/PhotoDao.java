package fr.eni.ecole.projetlocation.dao.photo;
import static fr.eni.ecole.projetlocation.dao.photo.IPhotoContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.SQLiteHelper;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.Photo;
import fr.eni.ecole.projetlocation.models.Vehicule;

/**
 * Created by Administrateur on 25/10/2017.
 */
public class PhotoDao {

    private static final String TAG = "VehiculeDAO" ;
    private SQLiteHelper sqLiteHelper;

    private SQLiteDatabase sqLiteDatabase;

    private String[] allColumns = {COLUMN_ID_PHOTOS, COLUMN_ID_EDL_PHOTOS,
            COLUMN_DATE_PHOTOS, COLUMN_URI_PHOTOS,COLUMN_ID_VEHICULE_PHOTOS};

    public PhotoDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public long createPhoto(Photo photo) {
        ContentValues values = new ContentValues();
        if(photo.getEdl() != null){
            values.put(COLUMN_ID_EDL_PHOTOS, photo.getEdl().getId());
        }else {
            values.put(COLUMN_ID_VEHICULE_PHOTOS, photo.getVehicule().getId());
        }
        values.put(COLUMN_DATE_PHOTOS, photo.getDate());
        values.put(COLUMN_URI_PHOTOS, photo.getUri());

        return sqLiteDatabase.insert(TABLE_PHOTOS, null, values);
    }

    public Cursor selectAllCursor() {
        return sqLiteDatabase.query(TABLE_PHOTOS, allColumns, null, null, null, null, null);
    }

    public List<Photo> selectPhotoByEdl(int id){
        List<Photo> photos = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_PHOTOS,allColumns,"id_edl ='"+id+"'",null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Photo photo = map(cursor);
            photos.add(photo);
            cursor.moveToNext();
        }
        cursor.close();
        return photos;
    }


    public Photo map(Cursor c) {
        Photo photo = new Photo();
        photo.setId(c.getInt(NUM_COL_ID_PHOTO));
        photo.setDate(c.getString(NUM_COL_DATE_PHOTO));
        photo.setUri(c.getString(NUM_COL_URI_PHOTO));
        EDL edl = new EDL();
        edl.setId(c.getInt(NUM_COL_ID_EDL));
        photo.setEdl(edl);

        Vehicule vehicule = new Vehicule();
        vehicule.setId(c.getInt(NUM_COL_ID_VEHICULE));
        photo.setVehicule(vehicule);
        return photo;
    }
}
