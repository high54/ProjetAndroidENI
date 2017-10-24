package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class DetailVehiculeActivity extends AppCompatActivity {

    private Vehicule vehicule;
    private VehiculeDao dao;
    private Button rendreLouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vehicule);

        Intent intent = getIntent();
        if (intent.hasExtra("vehicule")) {
            vehicule = intent.getExtras().getParcelable("vehicule");
        }

        if (null != vehicule) {
            TextView tv_marque = (TextView) findViewById(R.id.tv_marque);
            tv_marque.setText(vehicule.getMarque());

            TextView tv_model = (TextView) findViewById(R.id.tv_model);
            tv_model.setText(vehicule.getModel());

            TextView tv_prix = (TextView) findViewById(R.id.tv_prix);
            tv_prix.setText(String.valueOf(vehicule.getPrix()));

            TextView tv_carburant = (TextView) findViewById(R.id.tv_carburant);
            tv_carburant.setText(vehicule.getCarburant());

            TextView tv_type = (TextView) findViewById(R.id.tv_type_vehicule);

            if(Vehicule.TYPE_VILLE == vehicule.getType()) {
                tv_type.setText("Ville");
            }else {
                tv_type.setText("Hors Ville");
            }

            TextView tv_loue = (TextView) findViewById(R.id.tv_dispo);
            TextView tv_nom_client = (TextView) findViewById(R.id.tv_nom_client);
            TextView tv_date_location = (TextView) findViewById(R.id.tv_date_location);
            rendreLouer = (Button) findViewById(R.id.bt_rendre_louer);
            if (!vehicule.getLoue()) {
                tv_loue.setText(R.string.disponible);
                tv_nom_client.setText("");
                tv_date_location.setText("");
                rendreLouer.setText(R.string.louer_le_vehicule);
            } else {
                tv_loue.setText(R.string.non_dispo);
                tv_nom_client.setText("le client qui a la location :D");
                tv_date_location.setText("le 25/85/456 mouhahaha");
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
        Intent intent = new Intent(DetailVehiculeActivity.this, EtatDesLieux.class);
        intent.putExtra("vehicule", vehicule);
        if(vehicule.getLoue()){
            intent.putExtra("etat", "rendre");
        }
        else {
            intent.putExtra("etat", "louer");
        }
        startActivity(intent);
    }

    public void onClickShowHistorique(View view) {

    }
}
