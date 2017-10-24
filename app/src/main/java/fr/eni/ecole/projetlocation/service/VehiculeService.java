package fr.eni.ecole.projetlocation.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Vehicule;

/**
 * Created by bkrafft2016 on 24/10/2017.
 */
public class VehiculeService {

    public interface VehiculeServiceListener {
        void OnSelectVehicule(ArrayList<Vehicule> vehicules);
    }

    public static void selectAll(final VehiculeServiceListener listener, final Context context) {
        new AsyncTask<Void, Void, ArrayList<Vehicule>>() {
            @Override
            protected ArrayList<Vehicule> doInBackground(Void... voids) {
                return new VehiculeDao(context).selectAll();
            }

            @Override
            protected void onPostExecute(ArrayList<Vehicule> vehicules) {
                super.onPostExecute(vehicules);
                listener.OnSelectVehicule(vehicules);
            }
        }.execute();
    }

    public static void selectAllRent(final VehiculeServiceListener listener, final Context context, final Boolean bool) {
        new AsyncTask<Void, Void, ArrayList<Vehicule>>() {
            @Override
            protected ArrayList<Vehicule> doInBackground(Void... voids) {
                return new VehiculeDao(context).selectAllRent(bool);
            }

            @Override
            protected void onPostExecute(ArrayList<Vehicule> vehicules) {
                super.onPostExecute(vehicules);
                listener.OnSelectVehicule(vehicules);
            }
        }.execute();
    }
}
