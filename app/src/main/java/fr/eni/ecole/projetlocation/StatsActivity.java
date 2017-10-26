package fr.eni.ecole.projetlocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.eni.ecole.projetlocation.dao.location.LocationDao;
import fr.eni.ecole.projetlocation.models.LocationVehicule;

public class StatsActivity extends AppCompatActivity {

    WebView webView;

    private static final String TAG = "StatsActivity";

    private long total = 0;
    private long semaine = 0;
    private long mois = 0;

    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private static final int MY_PERMISSIONS_REQUEST_NETWORK_STATE = 1;

    private LocationDao dao;
    private List<LocationVehicule> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        PackageManager pm = this.getPackageManager();
        int hasPermForSms = pm.checkPermission(
                Manifest.permission.INTERNET,
                this.getPackageName());
        if (hasPermForSms != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }

        dao = new LocationDao(this);

        locations = dao.getLocationsForStats();

        for (LocationVehicule location : locations) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date retour;
                if (null != location.getRetour()) {
                    retour = df.parse(location.getRetour());
                } else {
                    retour = new Date();
                }
                Date depart = df.parse(location.getDepart());
                long differenceForAll = retour.getTime() - depart.getTime();
                differenceForAll = differenceForAll / 1000 / 60 / 60 / 24 + 1;
                int prix = location.getTarif();
                total = total + differenceForAll * prix;

                Calendar calWeek = Calendar.getInstance();
                calWeek.add(Calendar.DATE, -7);
                String sevenDays = df.format(calWeek.getTime());
                Date lastWeek = df.parse(sevenDays);

                if (depart.getTime() >= lastWeek.getTime()) {
                    semaine = semaine + differenceForAll * prix;
                }

                Calendar calMonth = Calendar.getInstance();
                calMonth.add(Calendar.DATE, -30);
                String thirtyDays = df.format(calMonth.getTime());
                Date lastMonth = df.parse(thirtyDays);

                if (depart.getTime()>= lastMonth.getTime()) {
                    mois = mois + differenceForAll * prix;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        long totalFinal = total - mois;
        long moisFinal = mois - semaine;

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl("http://chart.apis.google.com/chart?cht=p3&chs=250x100&chd=t:" + totalFinal + "," + moisFinal + "," + semaine + "&chl=total|mois|semaine");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    public void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                MY_PERMISSIONS_REQUEST_NETWORK_STATE);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET},
                MY_PERMISSIONS_REQUEST_INTERNET);
    }
}
