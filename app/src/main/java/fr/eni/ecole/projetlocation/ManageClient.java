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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ClientDao daoClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_client);

    }

    public void onClickSaveClient(View view) {
        edNom = (EditText) findViewById(R.id.ed_nom_client);
        edPrenom = (EditText) findViewById(R.id.ed_prenom_client);
        edTelephone = (EditText) findViewById(R.id.ed_telephone_client);
        edDateNaissance = (EditText) findViewById(R.id.ed_date_client);
        edAdresse = (EditText) findViewById(R.id.ed_adresse_client);
        edCodePostal = (EditText) findViewById(R.id.ed_codepostal_client);
        edVille = (EditText) findViewById(R.id.ed_ville_client);

        if(checkParam(edNom.getText(),0) && checkParam(edPrenom.getText(),0) && checkParam(edTelephone.getText(),1) && checkParam(edDateNaissance.getText(),0)){
            daoClient = new ClientDao(this);
            daoClient.open();
            Client client = new Client();
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
            Log.wtf("WTF", client.toString());
        }

    }

    public boolean checkParam(Editable param, int type){
        if(type == 0){
            if(param != null && param.toString() !=""){
                return true;
            }
            Log.wtf("WTF", "=>>>>>>>>>>>  FALSE STIRNG");

        }else {
            try{
                Integer.parseInt(param.toString());
                return true;
            }catch(NumberFormatException e){
                Log.wtf("WTF", "=>>>>>>>>>>>  FALSE INT");

                return false;

            }
        }
        Log.wtf("WTF", "=>>>>>>>>>>>  FALSE FIN");

        return false;
    }
}
