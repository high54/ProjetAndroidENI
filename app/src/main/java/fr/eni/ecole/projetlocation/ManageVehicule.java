package fr.eni.ecole.projetlocation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.vehicule.VehiculeDao;
import fr.eni.ecole.projetlocation.models.Photo;
import fr.eni.ecole.projetlocation.models.Vehicule;

public class ManageVehicule extends AppCompatActivity {

    Vehicule vehicule;
    EditText et_marque;
    EditText et_model;
    EditText et_immatriculation;
    RadioButton button_essence;
    RadioButton button_diesel;
    RadioButton button_gpl;
    RadioButton button_electrique;
    RadioButton button_ville;
    RadioButton button_hors_ville;
    EditText et_prix;
    VehiculeDao dao;

    private ImageView mImageView;

    private static final int TAKE_PHOTO_REQUEST = 1;
    private File photoFile;
    private List<Photo> photos = new ArrayList<>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicule);

        et_marque = (EditText) findViewById(R.id.et_marque);
        et_model = (EditText) findViewById(R.id.et_model);
        et_immatriculation = (EditText) findViewById(R.id.et_immatriculation);
        button_essence = (RadioButton) findViewById(R.id.rb_essence);
        button_diesel = (RadioButton) findViewById(R.id.rb_diesel);
        button_gpl = (RadioButton) findViewById(R.id.rb_gpl);
        button_electrique = (RadioButton) findViewById(R.id.rb_electrique);
        button_ville = (RadioButton) findViewById(R.id.rb_ville);
        button_hors_ville = (RadioButton) findViewById(R.id.rb_hors_ville);
        et_prix = (EditText) findViewById(R.id.et_prix);

        Intent intent = getIntent();
        if (intent != null) {
            vehicule = intent.getParcelableExtra("vehicule");
        }

        if (null != vehicule) {
            et_marque.setText(vehicule.getMarque());
            et_model.setText(vehicule.getModel());
            et_immatriculation.setText(vehicule.getImmatriculation());

            if (Vehicule.ESSENCE.equals(vehicule.getCarburant())) {
                button_essence.setChecked(true);
            } else if (Vehicule.DIESEL.equals(vehicule.getCarburant())) {
                button_diesel.setChecked(true);
            } else if (Vehicule.GPL.equals(vehicule.getCarburant())) {
                button_gpl.setChecked(true);
            } else if(Vehicule.ELECTRIQUE.equals(vehicule.getCarburant())) {
                button_electrique.setChecked(true);
            }

            if (Vehicule.TYPE_VILLE == vehicule.getType()) {
                button_ville.setChecked(true);
            } else if (Vehicule.TYPE_HORS_VILLE == vehicule.getType()) {
                button_hors_ville.setChecked(true);
            }

            et_prix.setText(String.valueOf(vehicule.getPrix()));
        }
    }

    public void onClickSaveVehicule(View view) {
        if (null == vehicule) {
            vehicule = new Vehicule();
        }
        vehicule.setMarque(et_marque.getText().toString());
        vehicule.setModel(et_model.getText().toString());
        vehicule.setImmatriculation(et_immatriculation.getText().toString().toUpperCase());

        if (button_essence.isChecked()) {
            vehicule.setCarburant(Vehicule.ESSENCE);
        } else if (button_diesel.isChecked()) {
            vehicule.setCarburant(Vehicule.DIESEL);
        } else if (button_gpl.isChecked()) {
            vehicule.setCarburant(Vehicule.GPL);
        } else if(button_electrique.isChecked()) {
            vehicule.setCarburant(Vehicule.ELECTRIQUE);
        }

        if (button_ville.isChecked()) {
            vehicule.setType(Vehicule.TYPE_VILLE);
        } else if (button_hors_ville.isChecked()) {
            vehicule.setType(Vehicule.TYPE_HORS_VILLE);
        }

        vehicule.setPrix((Integer.parseInt(et_prix.getText().toString())));

        dao = new VehiculeDao(this);
        if(-1 == vehicule.getId()){
            dao.createVehicule(vehicule);
        }
        else{
            dao.update(vehicule);
        }

        showCarsList(null);
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
            int targetW = 100;
            int targetH = 100;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            Photo photo = new Photo();

            Date dateDuJour = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(dateDuJour);

            photo.setDate(date);
            photo.setUri(photoFile.getAbsolutePath());
            photo.setVehicule(vehicule);
            photos.add(photo);
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
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        System.out.println(mCurrentPhotoPath);
        return image;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        System.out.println(mCurrentPhotoPath);
        mImageView = (ImageView) findViewById(R.id.img_1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println(mCurrentPhotoPath);
        mImageView = (ImageView) findViewById(R.id.img_1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showAddCar(MenuItem item) {
        Intent intent = new Intent(ManageVehicule.this, ManageVehicule.class);
        startActivity(intent);
    }
    public void showCarsList(MenuItem item) {
        Intent intent = new Intent(ManageVehicule.this, ListeVehiculeActivity.class);
        startActivity(intent);
    }

    public void showVehiculeSearch(MenuItem item) {
        Intent intent = new Intent(ManageVehicule.this, SearchVehicule.class);
        startActivity(intent);
    }

    public void showStats(MenuItem item) {
        Intent intent = new Intent(ManageVehicule.this, StatsActivity.class);
        startActivity(intent);
    }
}
