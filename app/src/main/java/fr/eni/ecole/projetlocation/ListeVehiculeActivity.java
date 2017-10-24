package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import fr.eni.ecole.projetlocation.adapter.VehiculeAdapter;
import fr.eni.ecole.projetlocation.models.Vehicule;
import fr.eni.ecole.projetlocation.service.VehiculeService;

public class ListeVehiculeActivity extends AppCompatActivity implements VehiculeService.VehiculeServiceListener{

    private ArrayList<Vehicule> vehicules;
    private VehiculeAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_vehicule);

        vehicules = new ArrayList<>();
        adapter = new VehiculeAdapter(this, R.layout.presentation_liste_vehicule, vehicules);
        listView = (ListView) findViewById(R.id.list_vehicules);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        VehiculeService.selectAll(this, this);
    }

    @Override
    public void OnSelectVehicule(ArrayList<Vehicule> vehicules) {
        this.vehicules.clear();
        this.vehicules.addAll(vehicules);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(ListeVehiculeActivity.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {

    }
}
