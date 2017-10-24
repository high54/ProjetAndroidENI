package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class ManageVehicule extends AppCompatActivity {

    Vehicule vehicule;
    EditText et_marque;
    EditText et_model;
    EditText et_immatriculation;
    RadioButton button_essence;
    RadioButton button_diesel;
    RadioButton button_gpl;
    RadioButton button_electrique;
    RadioButton button_ville;
    RadioButton button_hors_ville;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    EditText et_prix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicule);

        et_marque = (EditText) findViewById(R.id.et_marque);
        et_model = (EditText) findViewById(R.id.et_model);
        et_immatriculation = (EditText) findViewById(R.id.et_immatriculation);
        button_essence = (RadioButton) findViewById(R.id.rb_essence);
        button_diesel = (RadioButton) findViewById(R.id.rb_diesel);
        button_gpl = (RadioButton) findViewById(R.id.rb_gpl);
        button_electrique = (RadioButton) findViewById(R.id.rb_electrique);
        button_ville = (RadioButton) findViewById(R.id.rb_ville);
        button_hors_ville = (RadioButton) findViewById(R.id.rb_hors_ville);
        et_prix = (EditText) findViewById(R.id.et_prix);

        Intent intent = getIntent();
        if (intent != null) {
            vehicule = intent.getParcelableExtra("vehicule");
        }

        if (null != vehicule) {
            et_marque.setText(vehicule.getMarque());
            et_model.setText(vehicule.getModel());
            et_immatriculation.setText(vehicule.getImmatriculation());

            if (Vehicule.ESSENCE.equals(vehicule.getCarburant())) {
                button_essence.setChecked(true);
            } else if (Vehicule.DIESEL.equals(vehicule.getCarburant())) {
                button_diesel.setChecked(true);
            } else if (Vehicule.GPL.equals(vehicule.getCarburant())) {
                button_gpl.setChecked(true);
            } else if(Vehicule.ELECTRIQUE.equals(vehicule.getCarburant())) {
                button_electrique.setChecked(true);
            }

            if (Vehicule.TYPE_VILLE == vehicule.getType()) {
                button_ville.setChecked(true);
            } else if (Vehicule.TYPE_HORS_VILLE == vehicule.getType()) {
                button_hors_ville.setChecked(true);
            }

            et_prix.setText(String.valueOf(vehicule.getPrix()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void onClickSaveVehicule(View view) {
        if (null == vehicule) {
            vehicule = new Vehicule();
        }
        vehicule.setMarque(et_marque.getText().toString());
        vehicule.setModel(et_model.getText().toString());
        vehicule.setImmatriculation(et_immatriculation.getText().toString());

        if (button_essence.isChecked()) {
            vehicule.setCarburant(Vehicule.ESSENCE);
        } else if (button_diesel.isChecked()) {
            vehicule.setCarburant(Vehicule.DIESEL);
        } else if (button_gpl.isChecked()) {
            vehicule.setCarburant(Vehicule.GPL);
        } else if(button_electrique.isChecked()) {
            vehicule.setCarburant(Vehicule.ELECTRIQUE);
        }

        if (button_ville.isChecked()) {
            vehicule.setType(Vehicule.TYPE_VILLE);
        } else if (button_hors_ville.isChecked()) {
            vehicule.setType(Vehicule.TYPE_HORS_VILLE);
        }

        vehicule.setPrix((Integer.parseInt(et_prix.getText().toString())));

        VehiculeDao dao = new VehiculeDao(this);
        if(-1 == vehicule.getId()){
            dao.createVehicule(vehicule);
        }
        else{
            dao.update(vehicule);
        }
    }

    public void showAddCar(MenuItem item) {
    }
}
