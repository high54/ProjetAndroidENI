package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import fr.eni.ecole.projetlocation.dao.edl.EDLDao;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.LocationVehicule;

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
