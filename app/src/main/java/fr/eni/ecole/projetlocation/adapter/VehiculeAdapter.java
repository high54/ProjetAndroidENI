package fr.eni.ecole.projetlocation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.R;
import fr.eni.ecole.projetlocation.dao.photo.PhotoDao;
import fr.eni.ecole.projetlocation.models.Photo;
import fr.eni.ecole.projetlocation.models.Vehicule;

/**
 * Created by bkrafft2016 on 24/10/2017.
 */
public class VehiculeAdapter extends ArrayAdapter<Vehicule>{
    private Context contextGlobal;
    public VehiculeAdapter(Context context, int resource, List<Vehicule> objects) {
        super(context, resource, objects);
        contextGlobal = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewARetourner = inflater.inflate(R.layout.presentation_liste_vehicule, parent, false);
        Vehicule vehiculeAAfficher = getItem(position);
        PhotoDao daoPhotos = new PhotoDao(contextGlobal);
        List<Photo> photos = daoPhotos.selectPhotoByVehicule(vehiculeAAfficher.getId());
        ImageView img = (ImageView)viewARetourner.findViewById(R.id.liste_img);
        int targetW = 100;
        int targetH = 100;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        if(null != photos && photos.size() >0)
        {
            BitmapFactory.decodeFile(photos.get(0).getUri(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(photos.get(0).getUri(), bmOptions);
            img.setImageBitmap(bitmap);
        }
        else {
            img.setBackgroundColor(Color.DKGRAY);
        }
        TextView tv_marque = (TextView)viewARetourner.findViewById(R.id.tv_marque);
        tv_marque.setText(vehiculeAAfficher.getMarque());

        TextView tv_carburant = (TextView)viewARetourner.findViewById(R.id.tv_carburant);
        tv_carburant.setText(vehiculeAAfficher.getCarburant());

        TextView tv_model = (TextView)viewARetourner.findViewById(R.id.tv_model);
        tv_model.setText(vehiculeAAfficher.getModel());

        TextView tv_prix = (TextView)viewARetourner.findViewById(R.id.tv_prix);
        tv_prix.setText(String.valueOf(vehiculeAAfficher.getPrix()));

        return viewARetourner;
    }
}
