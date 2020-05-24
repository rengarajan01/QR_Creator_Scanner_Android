package com.UnlimitedNibble.QuickResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class showScanQRalone extends AppCompatActivity {

    ListView listView;

    InterstitialAd interstitialAd;

    DatabaseHelper myDB = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scan_q_ralone);

        //Interstitial Ad
        MobileAds.initialize(this, "ca-app-pub-9025755371161858~3488797995");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9025755371161858/9144797749");
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded(){
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });

        listView = findViewById(R.id.listve);

         ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();


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
    public void onBackPressed() {

        Intent in = new Intent(showScanQRalone.this,MainActivity.class);
        startActivity(in);
        CustomIntent.customType(showScanQRalone.this,"right-to-left");
        super.onBackPressed();
    }
}
