package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.eni.ecole.projetlocation.dao.client.ClientDao;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class ManageClient extends AppCompatActivity {

    private EditText edNom;
    private EditText edPrenom;
    private EditText edTelephone;
    private EditText edDateNaissance;
    private EditText edAdresse;
    private EditText edCodePostal;
    private EditText edVille;
    private Button btSaveClient;

    private Client client = null;
    private ClientDao daoClient;
    private Vehicule vehicule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_client);
        edNom = (EditText) findViewById(R.id.ed_nom_client);
        edPrenom = (EditText) findViewById(R.id.ed_prenom_client);
        edTelephone = (EditText) findViewById(R.id.ed_telephone_client);
        edDateNaissance = (EditText) findViewById(R.id.ed_date_client);
        edAdresse = (EditText) findViewById(R.id.ed_adresse_client);
        edCodePostal = (EditText) findViewById(R.id.ed_codepostal_client);
        edVille = (EditText) findViewById(R.id.ed_ville_client);
        daoClient = new ClientDao(this);
        Intent intent = getIntent();
        if(intent.hasExtra("client")){
            client = intent.getExtras().getParcelable("client");
        }
        if(intent.hasExtra("vehicule")){
            vehicule = intent.getExtras().getParcelable("vehicule");
        }
        if (client != null) {
            edNom.setText(client.getNom());
            edPrenom.setText(client.getPrenom());
            edTelephone.setText(String.valueOf(client.getTelephone()));
            edDateNaissance.setText(client.getDateNaissance());
            edAdresse.setText(client.getAdresse());
            edCodePostal.setText(String.valueOf(client.getCodePostal()));
            edVille.setText(client.getVille());
        }
    }

    public void onClickSaveClient(View view) {

        if (checkParam(edNom.getText(), 0) && checkParam(edPrenom.getText(), 0) && checkParam(edTelephone.getText(), 1)) {
            daoClient = new ClientDao(this);
            if (client == null) {
                client = new Client();
                defineClient();
                client = daoClient.insertClient(client);
            } else {
                client = daoClient.getClientsById(client);
                defineClient();
                client = daoClient.updateClient(client);
            }
            Intent intent = new Intent(this,EtatDesLieux.class);
            intent.putExtra("action","nouveau");
            intent.putExtra("vehicule",vehicule);
            intent.putExtra("client",client);
            startActivity(intent);
        }
    }

    private void defineClient() {
        client.setNom(edNom.getText().toString());
        client.setPrenom(edPrenom.getText().toString());
        client.setTelephone(Float.parseFloat(edTelephone.getText().toString()));
        client.setAdresse(edAdresse.getText().toString());
        client.setVille(edVille.getText().toString());
        client.setCodePostal(Integer.parseInt(edCodePostal.getText().toString()));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate;
        try {
            startDate = df.parse(edDateNaissance.getText().toString());
            String newDateString = df.format(startDate);
            client.setDateNaissance(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean checkParam(Editable param, int type) {
        Toast toastErreur = new Toast(this);

        if (type == 0) {
            if (param != null && param.toString() != "") {
                return true;
            } else {
                toastErreur.makeText(this, "Les champs '*' sont obligatoire", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            try {
                Integer.parseInt(param.toString());
                return true;
            } catch (NumberFormatException e) {
                toastErreur.makeText(this, "Numéro de téléphone non valide", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(ManageClient.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(ManageClient.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(ManageClient.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(ManageClient.this, StatsActivity.class);
        startActivity(intent);
    }

}
