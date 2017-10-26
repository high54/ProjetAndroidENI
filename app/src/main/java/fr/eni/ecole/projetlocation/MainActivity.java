package fr.eni.ecole.projetlocation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.projetlocation.dao.agence.AgenceDao;
import fr.eni.ecole.projetlocation.dao.client.ClientDao;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Agence;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class MainActivity extends AppCompatActivity {
    private AgenceDao agenceDao;
    private TextView txNomAgence;
    private EditText edNomAgence;
    private Button btSaveAgence;
    private Agence agence;
    Vehicule vehicule;
    Vehicule vehicule2;
    Client client;
    Client client2;

    EditText login;
    EditText motDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VehiculeDao vehiculeDao = new VehiculeDao(this);
        List<Vehicule> vehicules = vehiculeDao.selectAll();
        if(vehicules.size()==0){
            vehicule = new Vehicule(20, "KI123KI", Vehicule.TYPE_VILLE, "Fiat", "Punto", Vehicule.ESSENCE);
            vehiculeDao.createVehicule(vehicule);
            vehicule2 = new Vehicule(25, "LO123VF", Vehicule.TYPE_VILLE, "Peaugeot", "305", Vehicule.ESSENCE);
            vehiculeDao.createVehicule(vehicule2);
            Vehicule vehicule3 = new Vehicule(25, "GF456LO", Vehicule.TYPE_HORS_VILLE, "Citroën", "Picasso", Vehicule.DIESEL);
            vehiculeDao.createVehicule(vehicule3);
            Vehicule vehicule4 = new Vehicule(30, "JU123JU", Vehicule.TYPE_HORS_VILLE, "Wolkswagen", "Golf", Vehicule.DIESEL);
            vehiculeDao.createVehicule(vehicule4);
            Vehicule vehicule5 = new Vehicule(40, "BG852HY", Vehicule.TYPE_VILLE, "Mercedes", "Autre", Vehicule.ESSENCE);
            vehiculeDao.createVehicule(vehicule5);
        }

        ClientDao clientDao = new ClientDao(this);
        List<Client> clients = clientDao.getClients();
        if(clients.size()==0){
            client = new Client("Bonsoir", "Luc", 258412365, "1 rue de la Voiture", 25842, "Rennes", "12/12/2012");
            clientDao.insertClient(client);
            client2 = new Client("Madame", "Sylvie", 258412365, "1 rue de la Voiture", 25842, "Rennes", "12/12/2012");
            clientDao.insertClient(client2);
            Client client3 = new Client("Monsieur", "Heureux", 258412365, "1 rue de la Voiture", 25842, "Rennes", "12/12/2012");
            clientDao.insertClient(client3);
            Client client4 = new Client("Madame", "Calin", 258412365, "1 rue de la Voiture", 25842, "Rennes", "12/12/2012");
            clientDao.insertClient(client4);
        }


        agenceDao = new AgenceDao(this);
        agence = agenceDao.getAgence();

        if(null != agence && agence.getNom().equals("Agence Bonjour")){
            login = (EditText) findViewById(R.id.et_identifiant);
            motDePasse = (EditText) findViewById(R.id.et_mot_de_passe);

            txNomAgence = (TextView) findViewById(R.id.txt_nom_agence);
            edNomAgence = (EditText) findViewById(R.id.ed_nom_agence);
            btSaveAgence = (Button) findViewById(R.id.save_nom_agence);

            txNomAgence.setText(agence.getNom());

            btSaveAgence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (login.getText().toString().equals("pass") && motDePasse.getText().toString().equals("pass")) {
                        if (edNomAgence.getText().toString() != "") {
                            Agence agence = new Agence();
                            agence.setNom(edNomAgence.getText().toString());
                            agenceDao.updateAgence(agence);

                            agence = agenceDao.getAgence();
                            txNomAgence.setText(agence.getNom());
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Identification incorrecte: veuillez réessayer");
                        // Add the buttons
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
        else{
            Intent intent = new Intent(MainActivity.this, SearchVehicule.class);
            startActivity(intent);
        }
    }
}
