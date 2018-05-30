package fyp.protech360.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.classes.User;
import fyp.protech360.utils.GeofenceStruct;
import fyp.protech360.utils.Global;

/**
 * Created by Usman Rafi on 5/29/2018.
 */

public class GeofenceService extends Service {

    private User currentUser;

    private ArrayList<GeofenceStruct> geofenceStructs = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d("Usman_Geofencing", "Service started");

        currentUser = (User) intent.getSerializableExtra("user_name");
        Log.d("Usman_Geofencing",currentUser.getUuid());

        getStructs();
        getUserStatus();

        return START_REDELIVER_INTENT;
    }

    private void checkOutOfBounds(){
        getStatus();

        for(GeofenceStruct struct : geofenceStructs){
            if(struct.outOfBounds(Global.user_latitude, Global.user_longitude)){
                mFirebaseDatabase.getReference("OutOfBounds")
                        .child(currentUser.getUuid())
                        .child(struct.getUuid())
                        .setValue("True");
            }
        }
    }

    private void checkOutOfBounds(GeofenceStruct struct){

        if(struct.outOfBounds(Global.user_latitude, Global.user_longitude)){
            mFirebaseDatabase.getReference("OutOfBounds")
                    .child(currentUser.getUuid())
                    .child(struct.getUuid())
                    .setValue("True");
        }
    }


    private void getStatus(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Status");

        for(final GeofenceStruct struct: geofenceStructs){
            dbRef.child(struct.getUuid()).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String latLng = (String)dataSnapshot.getValue();

                            struct.setLatitude(Double.parseDouble(latLng.split(",")[0]));
                            struct.setLongitude(Double.parseDouble(latLng.split(",")[1]));

                            checkOutOfBounds(struct);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
        }
    }

    private void getUserStatus(){
        FirebaseDatabase.getInstance().getReference("Status")
                .child(currentUser.getUuid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String latLng = (String)dataSnapshot.getValue();

                Global.user_latitude = Double.parseDouble(latLng.split(",")[0]);
                Global.user_longitude = Double.parseDouble(latLng.split(",")[1]);

                checkOutOfBounds();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getStructs(){
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections")
                .child(currentUser.getUuid());

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final GeofenceStruct struct = new GeofenceStruct(dataSnapshot.getKey());

                Log.d("Usman_geofence_uuid", dataSnapshot.getKey());

                dbRef.child(dataSnapshot.getKey()).child("requestRange")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                struct.setRange(Integer.parseInt(String.valueOf(dataSnapshot.getValue())));

                                geofenceStructs.add(struct);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Usman_Geofencing", "Service stopped");
    }
}
