package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.projetlocation.dao.client.ClientDao;
import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.dao.photo.PhotoDao;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import fr.eni.ecole.projetlocation.models.Photo;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class DetailVehiculeActivity extends AppCompatActivity {

    private Vehicule vehicule;
    private VehiculeDao dao;
    private Button rendreLouer;
    private ClientDao clientDao;
    private LocationVehicule location;
    private LocationDao locationDao;
    private Client client;

    private TextView tv_loue;
    private TextView tv_nom_client;
    private TextView tv_date_location;
    private TextView tv_marque;
    private TextView tv_model;
    private TextView tv_prix;
    private TextView tv_carburant;
    private TextView tv_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vehicule);

        Intent intent = getIntent();
        if (intent.hasExtra("vehicule")) {
            vehicule = intent.getExtras().getParcelable("vehicule");
            Log.wtf("Detail Véhicule======> ", ":"+vehicule);
            Log.wtf("Detail Véhicule======> ", ":"+vehicule.getLoue());
        }

        if (null != vehicule) {
            PhotoDao daoPhotos = new PhotoDao(this);
            List<Photo> photos = daoPhotos.selectPhotoByVehicule(vehicule.getId());
            ImageView img = (ImageView)findViewById(R.id.image_detail);
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

            tv_marque = (TextView) findViewById(R.id.tv_marque);
            tv_marque.setText(vehicule.getMarque());

            tv_model = (TextView) findViewById(R.id.tv_model);
            tv_model.setText(vehicule.getModel());

            tv_prix = (TextView) findViewById(R.id.tv_prix);
            tv_prix.setText("Prix :"+String.valueOf(vehicule.getPrix()));

            tv_carburant = (TextView) findViewById(R.id.tv_carburant);
            tv_carburant.setText(vehicule.getCarburant());

            tv_type = (TextView) findViewById(R.id.tv_type_vehicule);

            if(Vehicule.TYPE_VILLE == vehicule.getType()) {
                tv_type.setText("Ville");
            }else {
                tv_type.setText("Hors Ville");
            }

            tv_loue = (TextView) findViewById(R.id.tv_dispo);
            tv_nom_client = (TextView) findViewById(R.id.tv_nom_client);
            tv_date_location = (TextView) findViewById(R.id.tv_date_location);
            rendreLouer = (Button) findViewById(R.id.bt_rendre_louer);
            if (!vehicule.getLoue()) {
                tv_loue.setText(R.string.disponible);
                tv_nom_client.setText("");
                tv_date_location.setText("");
                rendreLouer.setText(R.string.louer_le_vehicule);
            } else {
                tv_loue.setText(R.string.non_dispo);
                locationDao = new LocationDao(this);
                location = locationDao.getLastLocation(vehicule.getId());
                clientDao = new ClientDao(this);
                Log.wtf("WTF","LOCATION CLIENT =============> "+location.getClient().toString());
                client = clientDao.getClientsById(location.getClient());
                if (client != null) {
                    tv_nom_client.setText(client.getNom() + " " + client.getPrenom());
                }
                if(location != null) {
                    tv_date_location.setText("Départ du véhicule le : "+location.getDepart());
                }
                rendreLouer.setText(R.string.rendre_le_vehicule);
            }
        }
    }

    public void onClickEditCar(View view) {
        Intent intent = new Intent(DetailVehiculeActivity.this, ManageVehicule.class);
        intent.putExtra("vehicule", vehicule);
        startActivity(intent);
    }

    public void onClickSaveChanges(View view) {
        Intent intent;
        if(vehicule.getLoue()){
            intent = new Intent(DetailVehiculeActivity.this, EtatDesLieux.class);
            intent.putExtra("vehicule", vehicule);
            intent.putExtra("action", "rendre");
        }
        else {
            intent = new Intent(DetailVehiculeActivity.this, SearchClient.class);
            intent.putExtra("vehicule", vehicule);
            intent.putExtra("action", "louer");
        }
        startActivity(intent);
    }

    public void onClickShowHistorique(View view) {
        Intent intent = new Intent(DetailVehiculeActivity.this, HistoriqueLocationsActivity.class);
        intent.putExtra("vehicule", vehicule);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(DetailVehiculeActivity.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(DetailVehiculeActivity.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(DetailVehiculeActivity.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(DetailVehiculeActivity.this, StatsActivity.class);
        startActivity(intent);
    }
}
