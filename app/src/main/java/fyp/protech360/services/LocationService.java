package fyp.protech360.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fyp.protech360.utils.Global;



public class LocationService extends Service {

    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private String user;

    @SuppressLint("RestrictedApi")
    private void startLocationUpdates() {

        //TODO: Make a service so that the location updates even if the app isn't running
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getApplication());
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        LocationServices.getFusedLocationProviderClient(getApplication()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());


    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        try {
            String msg = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
            DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Status").child(user);
            firebaseDatabase.setValue(msg)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Usman", "On Scene");
                        }
                    });
        }
            // You can now create a LatLng Object for use with maps
            //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        catch(NullPointerException e){
            Log.d("Sajjad Ali", "Nullpointer Scene");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        user = intent.getStringExtra("user_name");
        startLocationUpdates();

        Log.d("Usman", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDestroy() {
        user = null;
        Log.d("Usman", "Service Stopped");
    }


}
