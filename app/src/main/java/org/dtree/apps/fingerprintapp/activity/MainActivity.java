package org.dtree.apps.fingerprintapp.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.simprints.libsimprints.*;

import org.dtree.apps.fingerprintapp.R;
import org.dtree.apps.fingerprintapp.base.AddoDatabase;
import org.dtree.apps.fingerprintapp.model.User;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button scanFingerPrint;
    TextView message;
    ImageView userImage;

    SimHelper simHelper = new SimHelper(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_PROJECT_ID, org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.dtree.apps.fingerprintapp.R.layout.activity_main);
        setupview();

    }

    private void setupview(){
        message = findViewById(R.id.message);
        userImage = findViewById(R.id.user_image);
    }

    public void register(View v){
        startActivity(new Intent(this, RegisterActivity.class));
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
        if (requestCode == 22){

            ArrayList<Identification> identifications =
                    data.getParcelableArrayListExtra(Constants.SIMPRINTS_IDENTIFICATIONS);

            Identification mostConfident;
            boolean found = false;
            boolean lowConfidence = false;
            User user;

            if (identifications.size() > 0){
                mostConfident = identifications.get(0);
                for (int i=0; i<identifications.size(); i++){
                    switch (identifications.get(i).getTier()){
                        case TIER_1:
                            mostConfident = identifications.get(i);
                            getUserDetails(mostConfident);
                            Log.d("Found", "Confidence "+mostConfident.getConfidence());
                            found = true;
                            break;
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
                        //getUserDetails(mostConfident);
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

    private void getUserDetails(Identification identification){

        final AddoDatabase database = AddoDatabase.getInstance(MainActivity.this);
        LiveData<User> userLiveData = database.userDao().getUserById(identification.getGuid());
        userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Log.d("Found", "Getting user from the database");
                if (user != null){
                    message.setText(user.getUserName());
                    Log.d("Found", "User image "+user.getUserImage());
                    Glide.with(MainActivity.this)
                            .load(user.getUserImage())
                            .centerCrop()
                            .into(userImage);
                }
            }
        });

    }

}
