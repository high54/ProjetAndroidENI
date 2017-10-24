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

public class SearchClient extends AppCompatActivity {

    private ClientDao daoClient;
    private  List<Client> clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        daoClient = new ClientDao(this);
        daoClient.open();

        clients = daoClient.getClients();

        ClientAdapter adapterClient = new ClientAdapter(this,R.layout.liste_client,clients);
        final ListView listView = (ListView) findViewById(R.id.lv_liste_client);
        listView.setAdapter(adapterClient);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Client clientSelectionne = (Client) listView.getItemAtPosition(position);
                Intent intent = new Intent(SearchClient.this,ManageClient.class);
                intent.putExtra("client",  clientSelectionne);
                startActivity(intent);
            }
        });
    }

    public void onClickSearchClient(View view) {
        EditText edNom = (EditText) findViewById(R.id.ed_nom_client);
        EditText edPrenom = (EditText) findViewById(R.id.ed_prenom_client);

        if(edNom.getText() != null && edPrenom.getText() != null && edNom.getText().toString() !="" && edPrenom.getText().toString() !="" ){
            List<Client> clients = daoClient.getClientsByNomPrenom(edNom.getText().toString(),edPrenom.getText().toString());
                ClientAdapter adapterClient = new ClientAdapter(this,R.layout.liste_client,clients);
                ListView listView = (ListView) findViewById(R.id.lv_liste_client);
                listView.setAdapter(adapterClient);
            }
        }

    public void onClickAddClient(View view) {
        Intent intent = new Intent(SearchClient.this,ManageClient.class);
        startActivity(intent);
    }

    protected void onResume(){
        super.onResume();
        daoClient = new ClientDao(this);
        daoClient.open();
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
}
