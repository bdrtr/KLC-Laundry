package com.kilicarslan.KLC.MainPages.SideMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.kilicarslan.KLC.R;

public class QRimage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrimage);
        CreateQR();
    }


    public void CreateQR() {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("sira_istiyorum", BarcodeFormat.QR_CODE, 400,400);
            ImageView imageViewQR = findViewById(R.id.save_button);
            imageViewQR.setImageBitmap(bitmap);

        } catch (Exception e) {
            Log.e("----",e.getMessage());
        }
    }
}