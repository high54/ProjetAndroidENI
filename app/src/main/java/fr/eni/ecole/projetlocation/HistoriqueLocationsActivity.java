package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import fr.eni.ecole.projetlocation.adapter.LocationAdapter;
import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class HistoriqueLocationsActivity extends AppCompatActivity {
    private Vehicule vehicule;
    private LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_locations);

        Intent intent = getIntent();
        if (intent.hasExtra("vehicule")) {
            vehicule = intent.getExtras().getParcelable("vehicule");
        }

        LocationDao dao = new LocationDao(this);
        dao.open();
        List<LocationVehicule> locations = dao.getAllLocation(vehicule.getId());

        if (0 != locations.size()) {
            ListView listView = (ListView) findViewById(R.id.list_locations);
            adapter = new LocationAdapter(this, R.layout.presentation_liste_locations, locations);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(HistoriqueLocationsActivity.this, ListeEtatDesLieux.class);
                    LocationVehicule locationVehicule = adapter.getItem(i);
                    intent.putExtra("location", locationVehicule);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(HistoriqueLocationsActivity.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(HistoriqueLocationsActivity.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(HistoriqueLocationsActivity.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(HistoriqueLocationsActivity.this, StatsActivity.class);
        startActivity(intent);
    }
}
