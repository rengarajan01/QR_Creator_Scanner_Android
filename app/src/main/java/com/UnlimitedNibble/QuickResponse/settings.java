package com.UnlimitedNibble.QuickResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed() {
        Intent inn = new Intent(settings.this,MainActivity.class);
        startActivity(inn);
        CustomIntent.customType(settings.this,"right-to-left");
        super.onBackPressed();
    }
}
