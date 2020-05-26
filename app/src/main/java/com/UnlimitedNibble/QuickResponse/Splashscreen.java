package com.UnlimitedNibble.QuickResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import maes.tech.intentanim.CustomIntent;

public class Splashscreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(Splashscreen.this,MainActivity.class);
                startActivity(in);
                CustomIntent.customType(Splashscreen.this,"fadein-to-fadeout");
            }
        },1000);
    }


}
