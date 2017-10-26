package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import fr.eni.ecole.projetlocation.adapter.ClientAdapter;
import fr.eni.ecole.projetlocation.dao.client.ClientDao;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class SearchClient extends AppCompatActivity {

    private ClientDao daoClient;
    private  List<Client> clients;
    private Vehicule vehicule;
    private EditText edPrenom;
    private EditText edNom;
    private ListView listView;
    private ClientAdapter adapterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        daoClient = new ClientDao(this);

        clients = daoClient.getClients();
        Intent intent = getIntent();
        if(intent.hasExtra("vehicule")){
            vehicule = intent.getExtras().getParcelable("vehicule");
        }

        ClientAdapter adapterClient = new ClientAdapter(this,R.layout.liste_client,clients);
        final ListView listView = (ListView) findViewById(R.id.lv_liste_client);
        listView.setAdapter(adapterClient);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Client clientSelectionne = (Client) listView.getItemAtPosition(position);
                Intent intent = new Intent(SearchClient.this,ManageClient.class);
                intent.putExtra("client",  clientSelectionne);
                intent.putExtra("vehicule",vehicule);
                startActivity(intent);
            }
        });
    }

    public void onClickSearchClient(View view) {
        edNom = (EditText) findViewById(R.id.ed_nom_client);
        edPrenom = (EditText) findViewById(R.id.ed_prenom_client);

        if(edNom.getText() != null && edPrenom.getText() != null && edNom.getText().toString() !="" && edPrenom.getText().toString() !="" ){
            clients = daoClient.getClientsByNomPrenom(edNom.getText().toString(),edPrenom.getText().toString());
                adapterClient = new ClientAdapter(this,R.layout.liste_client,clients);
                listView = (ListView) findViewById(R.id.lv_liste_client);
                listView.setAdapter(adapterClient);
            }
        }

    public void onClickAddClient(View view) {
        Intent intent = new Intent(SearchClient.this,ManageClient.class);
        intent.putExtra("vehicule",vehicule);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
        daoClient = new ClientDao(this);
        clients = daoClient.getClients();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(SearchClient.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(SearchClient.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(SearchClient.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(SearchClient.this, StatsActivity.class);
        startActivity(intent);
    }
}
