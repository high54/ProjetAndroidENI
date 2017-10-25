package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import fr.eni.ecole.projetlocation.dao.edl.EDLDao;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.LocationVehicule;

public class ListeEtatDesLieux extends AppCompatActivity {
    private LocationVehicule location;
    private EDLDao edlDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_etat_des_lieux);
        Intent intent = getIntent();
        if(intent.hasExtra("location")){
            location = intent.getExtras().getParcelable("location");
        }
        edlDao = new EDLDao(this);
        List<EDL> edls = edlDao.getEdlByLocation(location.getId());
    }
}
