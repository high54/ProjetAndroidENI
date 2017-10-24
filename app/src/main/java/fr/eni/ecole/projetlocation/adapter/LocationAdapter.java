package fr.eni.ecole.projetlocation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.projetlocation.R;
import fr.eni.ecole.projetlocation.models.LocationVehicule;

/**
 * Created by bkrafft2016 on 24/10/2017.
 */
public class LocationAdapter extends ArrayAdapter<LocationVehicule>{

    public LocationAdapter(Context context, int resource, List<LocationVehicule> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewARetourner = inflater.inflate(R.layout.presentation_liste_locations,parent,false);

        LocationVehicule locationAAfficher = getItem(position);

        TextView tv_dispo = (TextView)viewARetourner.findViewById(R.id.tv_dispo);
        if(null == locationAAfficher.getRetour())
        {
            tv_dispo.setText(R.string.en_cours);
        }
        else{
            tv_dispo.setText("");
        }

        TextView tv_date_retour = (TextView)viewARetourner.findViewById(R.id.date_retour);
        tv_date_retour.setText("Retour : "+locationAAfficher.getRetour());

        TextView tv_date_depart = (TextView)viewARetourner.findViewById(R.id.date_depart);
        tv_date_depart.setText("Départ : "+locationAAfficher.getDepart());

        return viewARetourner;

    }
}
