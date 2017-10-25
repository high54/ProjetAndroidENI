package fr.eni.ecole.projetlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class StatsActivity extends AppCompatActivity {
    private long total = 0;
    private long semaine = 0;
    private long mois = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocationDao dao = new LocationDao(this);
        dao.open();

        List<LocationVehicule> locations = dao.getLocationsForStats();

        for ( LocationVehicule location : locations){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date retour;
                if(null != location.getRetour()){
                    retour = df.parse(location.getRetour());
                }
                else{
                    retour = new Date();
                }
                Date depart = df.parse(location.getDepart());
                long differenceForAll = retour.getTime() - depart.getTime();
                differenceForAll = differenceForAll/1000/60/60/24+1;

                VehiculeDao vehiculeDao = new VehiculeDao(this);
                dao.open();
                Vehicule vehicule = vehiculeDao.selectById(location.getVehicule().getId());
                int prix = vehicule.getPrix();
                total = total + differenceForAll*prix;
                Log.wtf("Chiffre d'affaire TOTAL======>", String.valueOf(total));
                Log.wtf("prix", String.valueOf(prix));



                Calendar calWeek = Calendar.getInstance();
                calWeek.add(Calendar.DATE, -7);
                String sevenDays = df.format(calWeek.getTime());
                Date lastWeek = df.parse(sevenDays);

                //on compare la date de départ à la date d'il y a 7 jours
                //Si la date est inférieure, on additionne pour la semaine
                long differenceForSeven = lastWeek.getTime() -  depart.getTime();
                differenceForSeven = differenceForSeven/1000/60/60/24+1;
                Log.wtf("difference 7======>", String.valueOf(differenceForSeven));
                if(differenceForSeven < 7){
                    semaine = semaine + differenceForAll*prix;
                    Log.wtf("Chiffre d'affaire de la semaine======>", String.valueOf(semaine));
                }


                Calendar calMonth = Calendar.getInstance();
                calMonth.add(Calendar.DATE, -30);
                String thirtyDays = df.format(calMonth.getTime());
                Date lastMonth = df.parse(thirtyDays);

                //on compare la date de départ à la date d'il y a 30 jours
                //Si la date est inférieure, on additionne pour le mois
                long differenceForThirty = depart.getTime() - lastMonth.getTime();
                differenceForThirty = differenceForThirty/1000/60/60/24+1;
                Log.wtf("difference 30======>", String.valueOf(differenceForThirty));
                if(differenceForThirty < 30){
                    mois = mois + differenceForThirty*prix;
                    Log.wtf("Chiffre d'affaire du mois======>", String.valueOf(mois));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(StatsActivity.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(StatsActivity.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(StatsActivity.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {

    }
}
