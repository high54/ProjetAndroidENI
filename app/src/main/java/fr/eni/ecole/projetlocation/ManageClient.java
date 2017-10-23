package fr.eni.ecole.projetlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.eni.ecole.projetlocation.dao.client.ClientDao;
import fr.eni.ecole.projetlocation.models.Client;

public class ManageClient extends AppCompatActivity {

    private EditText edNom;
    private EditText edPrenom;
    private EditText edTelephone;
    private EditText edDateNaissance;
    private EditText edAdresse;
    private EditText edCodePostal;
    private EditText edVille;
    private Button btSaveClient;

    private Client client = null;
    private ClientDao daoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_client);
        edNom = (EditText) findViewById(R.id.ed_nom_client);
        edPrenom = (EditText) findViewById(R.id.ed_prenom_client);
        edTelephone = (EditText) findViewById(R.id.ed_telephone_client);
        edDateNaissance = (EditText) findViewById(R.id.ed_date_client);
        edAdresse = (EditText) findViewById(R.id.ed_adresse_client);
        edCodePostal = (EditText) findViewById(R.id.ed_codepostal_client);
        edVille = (EditText) findViewById(R.id.ed_ville_client);
        daoClient = new ClientDao(this);
        daoClient.open();
        List<Client> clients = new ArrayList<>();
        clients = daoClient.getClients();
        for(int i =0;i<clients.size(); i++){
            Log.wtf("WTF","LE CLIENT =>"+clients.toString());
        }

    }

    public void onClickSaveClient(View view) {

        if(checkParam(edNom.getText(),0) && checkParam(edPrenom.getText(),0) && checkParam(edTelephone.getText(),1) && checkParam(edDateNaissance.getText(),0)) {
            daoClient = new ClientDao(this);
            daoClient.open();
            Log.wtf("WTF","=========>>>>>" +client);
            if (client == null) {

                client = new Client();
                client.setNom(edNom.getText().toString());
                client.setPrenom(edPrenom.getText().toString());
                client.setTelephone(Integer.parseInt(edTelephone.getText().toString()));
                client.setAdresse(edAdresse.getText().toString());
                client.setVille(edVille.getText().toString());
                client.setCodePostal(Integer.parseInt(edCodePostal.getText().toString()));
                DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.FRANCE);
                try {

                    Date date = format.parse(edDateNaissance.getText().toString());
                    client.setDateNaissance(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                client = daoClient.insertClient(client);
            }
        }else if(client instanceof Client){
            client = daoClient.getClientsById(client);
            client.setNom(edNom.getText().toString());
            client.setPrenom(edPrenom.getText().toString());
            client.setTelephone(Integer.parseInt(edTelephone.getText().toString()));
            client.setAdresse(edAdresse.getText().toString());
            client.setVille(edVille.getText().toString());
            client.setCodePostal(Integer.parseInt(edCodePostal.getText().toString()));
            DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.FRANCE);
            try {

                Date date = format.parse(edDateNaissance.getText().toString());
                client.setDateNaissance(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            client = daoClient.updateClient(client);

        }

    }

    public boolean checkParam(Editable param, int type){
        Toast toastErreur = new Toast(this);

        if(type == 0){
            if(param != null && param.toString() !=""){
                return true;
            }else {
                toastErreur.makeText(this,"Les champs '*' sont obligatoire",Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            try{
                Integer.parseInt(param.toString());
                return true;
            }catch(NumberFormatException e){
                toastErreur.makeText(this,"Numéro de téléphone non valide",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}
