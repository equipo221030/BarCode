package barcode.employee.waves.barcode;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class ScanActivity extends AppCompatActivity {

    SurfaceView surface;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acctivity_scan);
        surface= (SurfaceView)findViewById(R.id.surface);
        surface.setZOrderMediaOverlay(true);
        holder=surface.getHolder();
      // barcodeDetector.getBarcodeView().getCameraSettings().setAutoFocusEnabled(true);
        barcodeDetector=new  BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        if(!barcodeDetector.isOperational()){
            Toast.makeText(getApplicationContext(),"Sorry",Toast.LENGTH_SHORT).show();
            this.finish();
        }
        cameraSource=new CameraSource.Builder(this,barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)

                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){cameraSource.start(surface.getHolder());}
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode>barcodes = detections.getDetectedItems();
                if(barcodes.size()>0){
                    Intent intent= new Intent();
                    intent.putExtra("barcode",barcodes.valueAt(0));
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });



    }



}
