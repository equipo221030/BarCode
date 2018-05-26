package barcode.employee.waves.barcode;

import android.Manifest;import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.vision.barcode.Barcode;
import java.util.ArrayList;
import java.util.HashMap;


public class Barcode_Activity extends AppCompatActivity {
    String uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;
    Button btn_scan;
    TextView result;
    public static final int PERMISSION_REQUIRED = 200;
    public static final int REQUEST_CODE = 100;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> menuItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_);
        result = (TextView) findViewById(R.id.name);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUIRED);
        }
        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Barcode_Activity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);

                    }
                });
            }
        }
    }
}
