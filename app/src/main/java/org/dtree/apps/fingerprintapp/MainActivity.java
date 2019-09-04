package org.dtree.apps.fingerprintapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simprints.libsimprints.*;

import java.nio.file.FileAlreadyExistsException;
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

            ArrayList<Identification> identifications =
                    data.getParcelableArrayListExtra(Constants.SIMPRINTS_IDENTIFICATIONS);

            Identification mostConfident;
            boolean found = false;
            boolean lowConfidence = false;

            if (identifications.size() > 0){
                mostConfident = identifications.get(0);
                for (int i=0; i<identifications.size(); i++){
                    switch (identifications.get(i).getTier()){
                        case TIER_1:
                            mostConfident = identifications.get(i);
                            found = true;
                            continue;
                        case TIER_2:
                            mostConfident = identifications.get(i);
                            found = true;
                            break;
                        case TIER_3:
                            mostConfident = identifications.get(i);
                            lowConfidence = true;
                            found = true;
                            break;
                        case TIER_4:
                            found = false;
                            break;
                        case TIER_5:
                            found = false;
                            break;
                    }
                }

                if (found){
                    if (!lowConfidence){
                        message.setText("User found!!!");
                    }else {
                        message.setText("User found with an OKAY match");
                    }
                }else {
                    message.setText("User not found");
                }

            }

        }

        //Boolean bcc = (Boolean) data.getParcelableExtra(Constants.SIMPRINTS_BIOMETRICS_COMPLETE_CHECK);
    }

}
