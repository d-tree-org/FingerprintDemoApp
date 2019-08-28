package org.dtree.apps.fingerprintapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simprints.libsimprints.*;


public class MainActivity extends AppCompatActivity {

    Button scanFingerPrint;
    TextView message;

    SimHelper simHelper = new SimHelper(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_PROJECT_ID, org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupview();

        scanFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = simHelper.register(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID);
                startActivityForResult(intent, 11);
            }
        });


    }

    private void setupview(){
        scanFingerPrint = findViewById(R.id.scan_fingerprint);
        message = findViewById(R.id.message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Here we check the request + result code


        // We can pull the unique ID from LibSimprints by creating a registration
        // object from the returned Intent data, and retrieving the GUID.

        Registration registration =
                data.getParcelableExtra(Constants.SIMPRINTS_REGISTRATION);
        String uniqueId = registration.getGuid();

        message.setText("Fingerprint ID : "+uniqueId);

        //Boolean bcc = (Boolean) data.getParcelableExtra(Constants.SIMPRINTS_BIOMETRICS_COMPLETE_CHECK);
    }

}
