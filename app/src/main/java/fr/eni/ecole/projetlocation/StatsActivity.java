package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.models.LocationVehicule;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocationDao dao = new LocationDao(this);
        dao.open();

        List<LocationVehicule> locations = dao.getLocationsForStats();

        for ( LocationVehicule location : locations){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date retour;
                if(null != location.getRetour()){
                    retour = df.parse(location.getRetour());
                }
                else{
                    retour = new Date();
                }
                Date depart = df.parse(location.getDepart());
                Log.wtf("retour", retour.toString());
                Log.wtf("depart", depart.toString());
                //int daysOfLocation = retour - depart;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(StatsActivity.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(StatsActivity.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(StatsActivity.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {

    }
}
