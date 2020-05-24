package com.UnlimitedNibble.QuickResponse;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import maes.tech.intentanim.CustomIntent;

public class CreateQR extends AppCompatActivity {

    ImageView iv;
    Button btn;
    TextInputEditText et;
    String text;
    Date date;
    String name;
    AdView adView;
    TextView tv;






    final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr);

        adView = findViewById(R.id.adView);
        iv = findViewById(R.id.imageView);
        btn = findViewById(R.id.btn1);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);



        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = Objects.requireNonNull(et.getText()).toString().trim();

                checkcontent();
            }
        });



        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CreateQR.this)
                        .setTitle("Help")
                        .setMessage("The Created QR Code is saved in the Quick Response Folder Present in the Root Directory")
                        .setNeutralButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }


    private void checkcontent() {

        if (text.isEmpty()) {
            Toast toast = Toast.makeText(this, "Empty Please Fill", Toast.LENGTH_SHORT);
            toast.show();
            et.setError("Empty String Not Allowed");

        } else {
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                iv.setImageBitmap(bitmap);
                saveimg(bitmap);

            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveimg(Bitmap bitmap) throws IOException {

        date = new Date();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-yyyy hh:mm:ss");
        name = dateFormat.format(date);

        File pathe = Environment.getExternalStorageDirectory();
        File pathe1 = new File(pathe.getAbsolutePath() + "/Quick Response/" + name + " CreatedQR.jpg");

        FileOutputStream outputStream = new FileOutputStream(pathe1);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();

        Toast.makeText(this, "QR Code is Sucessfully Created and Saved !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentbk = new Intent(CreateQR.this,MainActivity.class);
        startActivity(intentbk);
        CustomIntent.customType(CreateQR.this,"right-to-left");
    }
}
