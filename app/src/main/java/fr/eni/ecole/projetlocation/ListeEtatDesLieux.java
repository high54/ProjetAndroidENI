package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import fr.eni.ecole.projetlocation.dao.edl.EDLDao;
import fr.eni.ecole.projetlocation.dao.photo.PhotoDao;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import fr.eni.ecole.projetlocation.models.Photo;

public class ListeEtatDesLieux extends AppCompatActivity {
    private LocationVehicule location;
    private EDLDao edlDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etat_des_lieux);
        Intent intent = getIntent();
        if(intent.hasExtra("location")){
            location = intent.getExtras().getParcelable("location");
        }
        edlDao = new EDLDao(this);
        edlDao.open();
        List<EDL> edls = edlDao.getEdlByLocation(location.getId());

        PhotoDao photoDao = new PhotoDao(this);
        List<Photo> photos = photoDao.selectPhotoByEdl(edls.get(0).getId());



        RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.rl_liste_edl);
        for(int x=0;x<photos.size();x++) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            int targetW =40;
            int targetH = 40;
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            bmOptions.inSampleSize = scaleFactor;

            ImageView image = new ImageView(this);
            rl1.addView(image);

            ViewGroup.LayoutParams lp = image.getLayoutParams();
            Bitmap bitmap = BitmapFactory.decodeFile(photos.get(x).getUri(),bmOptions);
            image.setImageBitmap(bitmap);
            lp.height = 250;
            lp.width = 250;
            image.setLayoutParams(lp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(ListeEtatDesLieux.this, StatsActivity.class);
        startActivity(intent);
    }
}
