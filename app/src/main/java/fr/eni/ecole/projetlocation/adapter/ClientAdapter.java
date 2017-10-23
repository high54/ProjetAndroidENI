package fr.eni.ecole.projetlocation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fr.eni.ecole.projetlocation.R;
import fr.eni.ecole.projetlocation.models.Client;

/**
 * Created by Administrateur on 23/10/2017.
 */
public class ClientAdapter extends ArrayAdapter<Client> {

    public ClientAdapter(Context context, int resource, List<Client> objects) {
        super(context, resource, objects);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewARetourner = inflater.inflate(R.layout.liste_client,parent,false);

        TextView txtNom = (TextView) viewARetourner.findViewById(R.id.txt_nom_client);
        TextView txtPrenom = (TextView) viewARetourner.findViewById(R.id.txt_prenom_client);
        TextView txtTelephone = (TextView) viewARetourner.findViewById(R.id.txt_telephone_client);
        TextView txtDateNaissance = (TextView) viewARetourner.findViewById(R.id.txt_date_client);



        Client clientAAfficher = getItem(position);

        txtNom.setText(clientAAfficher.getNom());
        txtPrenom.setText(clientAAfficher.getPrenom());
        txtTelephone.setText(String.valueOf(clientAAfficher.getTelephone()));
        txtDateNaissance.setText(clientAAfficher.getDateNaissance().toString());

        return viewARetourner;
    }
}
