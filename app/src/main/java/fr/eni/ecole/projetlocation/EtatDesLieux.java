package fr.eni.ecole.projetlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.eni.ecole.projetlocation.dao.edl.EDLDao;
import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.LocationVehicule;

import fr.eni.ecole.projetlocation.models.Vehicule;

public class EtatDesLieux extends AppCompatActivity {
    private Vehicule vehicule;
    private Client client;
    private String action;
    private TextView txtDateJour;
    private String message;
    private String date;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_des_lieux);

        Intent intent = getIntent();
        txtDateJour = (TextView) findViewById(R.id.txt_date_jour);
        Date dateDuJour = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(dateDuJour);
        txtDateJour.setText(date);
        if(intent.hasExtra("action")){
            action = intent.getExtras().getString("action");
        }
        if (intent.hasExtra("client") && intent.hasExtra("vehicule")) {
            vehicule = intent.getExtras().getParcelable("vehicule");
            client = intent.getExtras().getParcelable("client");
        }

        if(action == "louer"){

        }else{

        }
        message = "Confirmation de la location le "+date+" du véhicule : "+vehicule.getMarque()+" "+vehicule.getModel();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
    }

    public void onClickSaveLocation(View view) {
        Log.wtf("WTF",""+client.getTelephone());
        LocationDao locationDao = new LocationDao(this);
         LocationVehicule location = new LocationVehicule();

        if(action =="rendre"){
            location.setRetour(date);
        }else{
            location.setVehicule(vehicule);
            location.setClient(client);
            location.setDepart(date);
            locationDao.open();
            location = locationDao.insertLocation(location);
            EDL edl = new EDL();
            edl.setDate(date);
            edl.setLocation(location);
            EDLDao edlDao = new EDLDao(this);
            edlDao.open();
            edlDao.insertEDL(edl);
            SmsManager.getDefault().sendTextMessage("0"+client.getTelephone(), null,message,null,null);
            Toast.makeText(this, "Sms de confirmation envoyé !", Toast.LENGTH_LONG).show();

        }

    }
}
