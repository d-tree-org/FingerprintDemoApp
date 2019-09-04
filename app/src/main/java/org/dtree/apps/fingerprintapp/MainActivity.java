package org.dtree.apps.fingerprintapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simprints.libsimprints.*;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button scanFingerPrint;
    TextView message;

    SimHelper simHelper = new SimHelper(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_PROJECT_ID, org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupview();


    }

    private void setupview(){
        message = findViewById(R.id.message);
    }

    public void register(View v){
        Intent intent = simHelper.register(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID);
        startActivityForResult(intent, 11);
    }

    public void identify(View v){
        Intent intent = simHelper.identify(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID); // The module to search in
        startActivityForResult(intent, 22);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Here we check the request + result code


        // We can pull the unique ID from LibSimprints by creating a registration
        // object from the returned Intent data, and retrieving the GUID.

        if (requestCode == 11){
            //Register
            Registration registration =
                    data.getParcelableExtra(Constants.SIMPRINTS_REGISTRATION);
            String uniqueId = registration.getGuid();

            message.setText("Fingerprint ID : "+uniqueId);

        }else if (requestCode == 22){

            //Identify
            Identification identification =
                    data.getParcelableExtra("identification");

            try{
                identification.getGuid();
                identification.getConfidence();
                identification.getTier();

                ArrayList<Identification> identifications =
                        data.getParcelableArrayListExtra(Constants.SIMPRINTS_IDENTIFICATIONS);
                for (Identification id : identifications) {
                    id.getGuid();
                    id.getConfidence();
                    id.getTier();

                    String uniqueId = id.getGuid();
                    message.setText(" User identified with ID : "+uniqueId);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        //Boolean bcc = (Boolean) data.getParcelableExtra(Constants.SIMPRINTS_BIOMETRICS_COMPLETE_CHECK);
    }

}
