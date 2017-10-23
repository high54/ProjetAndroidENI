package fr.eni.ecole.projetlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.eni.ecole.projetlocation.dao.AgenceDao;
import fr.eni.ecole.projetlocation.models.ModelAgence;

public class MainActivity extends AppCompatActivity {
    private AgenceDao agenceDao;
    private TextView txNomAgence;
    private EditText edNomAgence;
    private Button btSaveAgence;
    private ModelAgence agence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agenceDao = new AgenceDao(this);
        agenceDao.open();
        txNomAgence = (TextView) findViewById(R.id.txt_nom_agence);
        edNomAgence = (EditText) findViewById(R.id.ed_nom_agence);
        btSaveAgence = (Button) findViewById(R.id.save_nom_agence);
        agence = agenceDao.getAgence();
        txNomAgence.setText(agence.getNom());

        btSaveAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edNomAgence.getText().toString()!=""){
                    ModelAgence agence = new ModelAgence();
                    agence.setNom(edNomAgence.getText().toString());
                    agenceDao.updateAgence(agence);

                    agence = agenceDao.getAgence();
                    txNomAgence.setText(agence.getNom());

                }
            }
        });

    }
}
