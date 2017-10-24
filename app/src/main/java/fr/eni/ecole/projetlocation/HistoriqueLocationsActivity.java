package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        List<LocationVehicule> locations = dao.getAllLocation(vehicule.getId());

        ListView listView = (ListView)findViewById(R.id.list_locations);
        adapter = new LocationAdapter(this, R.layout.presentation_liste_locations, locations);
        listView.setAdapter(adapter);
    }
}
