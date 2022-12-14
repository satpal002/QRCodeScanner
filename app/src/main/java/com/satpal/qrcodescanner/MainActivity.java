package com.satpal.qrcodescanner;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.barcode.Barcode;
import com.satpal.qrscannerlib.BarcodeReader;

import java.util.List;


public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BarcodeReader barcodeReader;

    Boolean useFlash = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useFlash = !useFlash;
                barcodeReader.useFlash(useFlash);
            }
        });

        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");

        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         * */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }

    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}