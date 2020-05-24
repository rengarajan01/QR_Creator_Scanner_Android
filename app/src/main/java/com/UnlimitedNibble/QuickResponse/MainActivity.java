package com.UnlimitedNibble.QuickResponse;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    Button scan;
    Button create;
    String direct;
    int STORAGE11 = 1;
    int CAMERA = 2;
    int INTERNET_PERMISSION = 3;
    AdView adView;
    AdView Madview;
    int i = 1;
    int LOCATION_PERMISSSION = 4;
    InterstitialAd interstitialAd;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar toolbar = findViewById(R.id.tbar);
//        setSupportActionBar(toolbar);

        adintialize();
        inerstitialadload();
        checkPermission();


        final Intent in = new Intent(this, ScanQR.class);
        final Intent in2 = new Intent(this, CreateQR.class);

        checkfldr();
        scan = findViewById(R.id.scan);
        create = findViewById(R.id.create);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(in);
                CustomIntent.customType(MainActivity.this, "left-to-right");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(in2);
                CustomIntent.customType(MainActivity.this, "left-to-right");
            }
        });


    }

    private void inerstitialadload() {

        if(count == 0) {

            //Interstitial Ad
            MobileAds.initialize(this, "ca-app-pub-9025755371161858~3488797995");
            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-9025755371161858/7572216848");
            AdRequest request = new AdRequest.Builder().build();
            interstitialAd.loadAd(request);
            interstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }
            });

            count++;
        }

        else{

        }

    }


    private void adintialize() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        Madview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        Madview.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setTitle("Exit")
                        .setMessage("Are you Sure to exit the application ?")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory(Intent.CATEGORY_HOME);
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);
                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;

//            case R.id.settings:
//                Intent settins = new Intent(MainActivity.this, settings.class);
//                startActivity(settins);
//                CustomIntent.customType(MainActivity.this, "left-to-right");
//                break;

            case R.id.scandcode:
                Intent inten =  new Intent(MainActivity.this, showScanQRalone.class);
                startActivity(inten);
                CustomIntent.customType(MainActivity.this,"left-to-right");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            doNothing();
        } else {
            getpermission();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            doNothing();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            i++;
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            doNothing();
        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSSION);
        }


    }

    private void doNothing() {
        i++;
    }

    private void getpermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission is Needed To STORE the created QR code")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE11);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE11);
        }

    }

    private void checkfldr() {
        boolean ceckfld = true;
        direct = Environment.getRootDirectory().toString();
        File folder = new File(Environment.getExternalStorageDirectory() + "/Quick Response");
        if (!folder.exists()) {
            ceckfld = folder.mkdir();
        }
        if (!ceckfld) {
            Toast toast = Toast.makeText(this, "Not Possible enable permission", Toast.LENGTH_LONG);
            toast.show();

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you Sure to exit the application ?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                })
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
}
