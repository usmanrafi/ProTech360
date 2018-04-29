package fyp.protech360.services;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.utils.Global;


public class ConnectionLocationMonitoringService extends Service {

    private GeofencingClient geofencingClient;
    int safetyRange;
    ArrayList<String> connections;

    @SuppressLint("RestrictedApi")
    private void startLocationUpdates() {

        connections = new ArrayList<>();

        final DatabaseReference connectionId = FirebaseDatabase.getInstance().getReference("Users").child(Global.currentUser.getUuid());
        connectionId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    connections.add(ds.getKey().toString());
                }
                DatabaseReference connectionLocation = FirebaseDatabase.getInstance().getReference("Status");
                connectionLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            String connID = ds.getKey();
                            if(connections.contains(connID))
                            {
                                DatabaseReference getRange = FirebaseDatabase.getInstance().getReference("Connections").child(Global.currentUser.getUuid()).child(connID).child("requestRange");
                                getRange.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        safetyRange = (int) dataSnapshot.getValue();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                String connLocation = ds.getValue().toString();
                                String[] latLng = connLocation.split(",");
                                Double lat = Double.parseDouble(latLng[0]);
                                Double lng = Double.parseDouble(latLng[1]);
                                Geofence geofence = getGeofence(lat, lng, connID);
                                geofencingClient.addGeofences(getGeofencingRequest(geofence),
                                        getGeofencePendingIntent(connID))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                }else{
                                                }
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private Geofence getGeofence(Double lat, Double lng, String connID) {
        return new Geofence.Builder()
                .setRequestId(connID)
                .setCircularRegion(lat, lng, safetyRange)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT)
                .setLoiteringDelay(1000)
                .build();
    }

    private PendingIntent getGeofencePendingIntent(String name) {
        Intent intent = new Intent(getApplicationContext(), SafetyRangeService.class);
        intent.putExtra("connectionName",name);
        return PendingIntent.getService(getApplicationContext(),0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_EXIT);
        builder.addGeofence(geofence);
        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startLocationUpdates();

        Log.d("Safety", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("Safety", "Service Stopped");
        stopSelf();
    }


}
