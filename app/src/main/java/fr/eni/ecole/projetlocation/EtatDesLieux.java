package fr.eni.ecole.projetlocation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.edl.EDLDao;
import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.dao.photo.PhotoDao;
import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Client;
import fr.eni.ecole.projetlocation.models.EDL;
import fr.eni.ecole.projetlocation.models.LocationVehicule;
import fr.eni.ecole.projetlocation.models.Photo;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class EtatDesLieux extends AppCompatActivity {
    private Vehicule vehicule;
    private Client client;
    private String action;
    private TextView txtDateJour;
    private String message;
    private String date;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private ImageView mImageView;
    private EDL edl = new EDL();

    private static final int TAKE_PHOTO_REQUEST = 1;
    private File photoFile;
    private List<Photo> photos = new ArrayList<>();
    Context context = this;

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
        if (intent.hasExtra("action")) {
            action = intent.getExtras().getString("action");
        }
        if (intent.hasExtra("client") && intent.hasExtra("vehicule")) {
            vehicule = intent.getExtras().getParcelable("vehicule");
            client = intent.getExtras().getParcelable("client");
        }
        mImageView = (ImageView) findViewById(R.id.iv_edl);

        message = "Confirmation de la location le " + date + " du véhicule : " + vehicule.getMarque() + " " + vehicule.getModel();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }
    }

    public void onClickSaveLocation(View view) {
        final VehiculeDao vehiculeDao = new VehiculeDao(this);
        Log.wtf("WTF", "" + client.getTelephone());
        final LocationDao locationDao = new LocationDao(this);
        final LocationVehicule[] location = {new LocationVehicule()};

        if (action == "rendre") {
            location[0].setRetour(date);
            vehicule.setLoue(false);
            vehiculeDao.update(vehicule);
        } else {
            location[0].setVehicule(vehicule);
            location[0].setClient(client);
            location[0].setDepart(date);
            location[0].setTarif(vehicule.getPrix());

            AlertDialog.Builder builder = new AlertDialog.Builder(EtatDesLieux.this);
            builder.setMessage("Êtes vous sur de vouloir comfirmer la location?");
            // Add the buttons
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    locationDao.open();
                    location[0] = locationDao.insertLocation(location[0]);
                    edl = new EDL();
                    edl.setDate(date);
                    edl.setLocation(location[0]);
                    EDLDao edlDao = new EDLDao(context);
                    edlDao.open();
                    edl = edlDao.insertEDL(edl);
                    SmsManager.getDefault().sendTextMessage("0" + client.getTelephone(), null, message, null, null);
                    Toast.makeText(context, "Sms de confirmation envoyé !", Toast.LENGTH_LONG).show();

                    vehicule.setLoue(true);
                    vehiculeDao.update(vehicule);
                    PhotoDao photoDao = new PhotoDao(context);
                    for (int i = 0; i < photos.size(); i++) {
                        photos.get(i).setEdl(edl);
                        photoDao.createPhoto(photos.get(i));
                    }
                    Log.wtf("WTF", "LA PHOTO =========>>>>> ");

                    Intent intent = new Intent(EtatDesLieux.this, SearchVehicule.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void onClickTakePhoto(View view) throws IOException {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "fr.eni.ecole.projetlocation.fileprovider", photoFile));
            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_REQUEST && resultCode == RESULT_OK) {

            // set the dimensions of the image
            int targetW = 100;
            int targetH = 100;

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            Photo photo = new Photo();
            photo.setDate(date);
            photo.setUri(photoFile.getAbsolutePath());
            photo.setEdl(edl);
            photos.add(photo);
            // stream = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
            mImageView.setImageBitmap(bitmap);
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(getFilesDir(), "images");
        storageDir.mkdirs();
        File image = new File(storageDir, imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        System.out.println(mCurrentPhotoPath);
        return image;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        System.out.println(mCurrentPhotoPath);
        mImageView = (ImageView) findViewById(R.id.iv_edl);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println(mCurrentPhotoPath);
        mImageView = (ImageView) findViewById(R.id.iv_edl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(EtatDesLieux.this, ManageVehicule.class);
        startActivity(intent);
    }

    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(EtatDesLieux.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(EtatDesLieux.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(EtatDesLieux.this, StatsActivity.class);
        startActivity(intent);
    }
}
