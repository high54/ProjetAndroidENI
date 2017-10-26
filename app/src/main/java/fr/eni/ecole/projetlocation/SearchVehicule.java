package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eni.ecole.projetlocation.adapter.VehiculeAdapter;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class SearchVehicule extends AppCompatActivity {
    private TextView txtPrix;
    private SeekBar sbPrix;
    private CheckBox cbType;
    private Spinner spMarque;
    private Spinner spCarburant;
    private Spinner spDiponibilite;
    private VehiculeDao daoVehicule;
    private List<Vehicule> vehicules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vehicule);
        daoVehicule = new VehiculeDao(this);
        sbPrix = (SeekBar) findViewById(R.id.sb_select_prix);
        txtPrix = (TextView) findViewById(R.id.txt_prix);
        sbPrix.setMax(150);
        sbPrix.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean b) {
                txtPrix.setText(String.valueOf(position));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        String[] marques = daoVehicule.selectMarque();

        String[] carburants = {
                Vehicule.ESSENCE,
                Vehicule.DIESEL,
                Vehicule.GPL,
                Vehicule.ELECTRIQUE
        };
        String[] dispo = {
                "Disponible",
                "Non-Disponible"
        };

        spCarburant = (Spinner) findViewById(R.id.sp_carburant);
        spDiponibilite = (Spinner) findViewById(R.id.sp_dispo);
        spMarque = (Spinner) findViewById(R.id.sp_marque);
        cbType = (CheckBox) findViewById(R.id.cb_type);

        ArrayAdapter<String> adapterDisponibilite = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dispo);
        spDiponibilite.setAdapter(adapterDisponibilite);

        ArrayAdapter<String> adapterCarburant = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, carburants);
        spCarburant.setAdapter(adapterCarburant);

        ArrayAdapter<String> adapterMarque = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, marques);
        spMarque.setAdapter(adapterMarque);
    }

    public void onClickSearchVehicule(View view) {
        String dispo = spDiponibilite.getSelectedItem().toString();
        String marque = spMarque.getSelectedItem().toString();
        String carburant = spCarburant.getSelectedItem().toString();
        int type = Vehicule.TYPE_HORS_VILLE;
        int disponibilite = 1;
        if (cbType.isChecked()) {
            type = Vehicule.TYPE_VILLE;
        }
        if (dispo == "Disponible") {
            disponibilite = 0;
        }

        vehicules = daoVehicule.selectSearchVehicule(marque, carburant, Integer.parseInt(txtPrix.getText().toString()), type, disponibilite);


        VehiculeAdapter vehiculeAdapter = new VehiculeAdapter(this, R.layout.list_vehicule, vehicules);
        final ListView listView = (ListView) findViewById(R.id.lv_liste_vehicule);
        listView.setAdapter(vehiculeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vehicule vehicule = (Vehicule) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(SearchVehicule.this, SearchClient.class);
                intent.putExtra("vehicule", vehicule);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(SearchVehicule.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(SearchVehicule.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(SearchVehicule.this, StatsActivity.class);
        startActivity(intent);
    }
}
