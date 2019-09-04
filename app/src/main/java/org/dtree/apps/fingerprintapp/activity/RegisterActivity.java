package org.dtree.apps.fingerprintapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.simprints.libsimprints.Constants;
import com.simprints.libsimprints.Identification;
import com.simprints.libsimprints.Registration;
import com.simprints.libsimprints.SimHelper;

import org.dtree.apps.fingerprintapp.R;
import org.dtree.apps.fingerprintapp.base.AddoDatabase;
import org.dtree.apps.fingerprintapp.model.User;

/**
 * Author : Isaya Mollel on 04/09/2019.
 */
public class RegisterActivity extends AppCompatActivity {

    EditText userNames;
    AddoDatabase database;
    SimHelper simHelper = new SimHelper(
            org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_PROJECT_ID,
            org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID );

    String newUserId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupviews();
    }

    private void setupviews(){
        setContentView(R.layout.activity_register);
        userNames = findViewById(R.id.usernames);
    }

    public void takePicture(View view){
        //Nothing happens for now
    }

    public void scanFingerprint(View view){
        Intent intent = simHelper.register(org.dtree.apps.fingerprintapp.util.Constants.SIMPRINTS_MODULE_ID);
        startActivityForResult(intent, 11);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Here we check the request + result code

        if (requestCode == 11){
            Registration registration =
                    data.getParcelableExtra(Constants.SIMPRINTS_REGISTRATION);
            newUserId = registration.getGuid();
        }

    }

    @SuppressLint("StaticFieldLeak")
    public void register(View view){
        final User newUser = new User();
        newUser.setUserName(userNames.getText().toString());
//        newUser.setUserImage("https://pbs.twimg.com/profile_images/1089228849727062017/T7CnBGlP_400x400.jpg");
        newUser.setUserImage("https://zenbooks.ca/wp-content/uploads/2017/09/placeholder-female-square.png");

        newUser.setUserId(newUserId);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {

                database = AddoDatabase.getInstance(RegisterActivity.this);
                database.userDao().createUser(newUser);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                finish();

            }
        }.execute();

    }

}
