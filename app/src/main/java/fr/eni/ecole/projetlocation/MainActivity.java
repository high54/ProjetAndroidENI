package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.eni.ecole.projetlocation.dao.AgenceDao;
import fr.eni.ecole.projetlocation.models.Agence;

public class MainActivity extends AppCompatActivity {
    private AgenceDao agenceDao;
    private TextView txNomAgence;
    private EditText edNomAgence;
    private Button btSaveAgence;
    private Agence agence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agenceDao = new AgenceDao(this);
        txNomAgence = (TextView) findViewById(R.id.txt_nom_agence);
        edNomAgence = (EditText) findViewById(R.id.ed_nom_agence);
        btSaveAgence = (Button) findViewById(R.id.save_nom_agence);
        agence = agenceDao.getAgence();
        txNomAgence.setText(agence.getNom());

        btSaveAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edNomAgence.getText().toString()!=""){
                    Agence agence = new Agence();
                    agence.setNom(edNomAgence.getText().toString());
                    agenceDao.updateAgence(agence);

                    agence = agenceDao.getAgence();
                    txNomAgence.setText(agence.getNom());

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,ListeVehiculeActivity.class);
        startActivity(intent);
    }
}
