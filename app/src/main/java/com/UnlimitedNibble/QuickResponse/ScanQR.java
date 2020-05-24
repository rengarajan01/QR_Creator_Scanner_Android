package com.UnlimitedNibble.QuickResponse;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class ScanQR extends AppCompatActivity {

    Button scan;
    AdView adView;

    ListView listView;



    DatabaseHelper myDB = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);


        scan = findViewById(R.id.scanagain);
        adView = findViewById(R.id.adView);

        listView = findViewById(R.id.listtview);


        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(ScanQR.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Align in the Square");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);

                integrator.setCaptureActivity(CapturePotraitActivity.class);
                integrator.setOrientationLocked(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
                showListView();
            }
        });


        IntentIntegrator integrator = new IntentIntegrator(ScanQR.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Align in the Square");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CapturePotraitActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();


        showListView();

    }

    private void showListView() {

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();

        StringBuffer buffer = new StringBuffer();


        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }

        else {

            while (data.moveToNext()) {

                theList.add(data.getString(0));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {

                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(this)
                        .setTitle("Message In the QR")
                        .setMessage(result.getContents())
                        .setPositiveButton("COPY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("Text Copied",result.getContents());
                                assert clipboardManager != null;
                                clipboardManager.setPrimaryClip(clip);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();

                adddata(result.getContents());



            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void adddata(String contents) {

        boolean sucessinsert = myDB.AddData(contents);
        if(!sucessinsert){
            adddata(contents);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        CustomIntent.customType(this,"right-to-left");
    }
}
