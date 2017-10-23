package fr.eni.ecole.projetlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import fr.eni.ecole.projetlocation.adapter.ClientAdapter;
import fr.eni.ecole.projetlocation.dao.client.ClientDao;
import fr.eni.ecole.projetlocation.models.Client;

public class SearchClient extends AppCompatActivity {

    private ClientDao daoClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        daoClient = new ClientDao(this);
        daoClient.open();

        List<Client> clients = daoClient.getClients();

        ClientAdapter adapterClient = new ClientAdapter(this,R.layout.liste_client,clients);
        ListView listView = (ListView) findViewById(R.id.lv_liste_client);
        listView.setAdapter(adapterClient);
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

}
