package fyp.protech360.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fyp.protech360.IBaseGPSListener;
import fyp.protech360.utils.Global;

/**
 * Created by Aliyan on 3/30/2018.
 */

public class SendLocationService extends IntentService {


    public SendLocationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        IBaseGPSListener i = new IBaseGPSListener() {
            @Override
            public void onGpsStatusChanged(int event) {

            }

            @Override
            public void onLocationChanged(Location location) {
                DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Status").child(Global.currentUser.getUuid());
                firebaseDatabase.setValue(location)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Sajjad Ali","Location Updated");
                            }
                        });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
}
