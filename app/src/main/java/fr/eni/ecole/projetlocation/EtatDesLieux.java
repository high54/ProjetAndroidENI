package fr.eni.ecole.projetlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.edl.EDLDao;
import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;


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


        message = "Confirmation de la location le "+date+" du véhicule : "+vehicule.getMarque()+" "+vehicule.getModel();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_ID_MULTIPLE_PERMISSIONS);
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

            VehiculeDao vehiculeDao = new VehiculeDao(this);
            vehicule.setLoue(true);
            vehiculeDao.update(vehicule);
        }

    }

    public void onClickTakePhoto(View view) {
        dispatchTakePictureIntent();
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("ERROR","DispatchTakePicture===>>> "+ ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "fr.eni.ecole.projetlocation.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView = (ImageView) findViewById(R.id.iv_edl);
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"/Android/data/fr.eni.ecole.projetlocation.fileprovider/files/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void setPic() {
        ImageView mImageView = (ImageView) findViewById(R.id.iv_edl);
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

}
