package fyp.protech360.ui;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import fyp.protech360.R;
import fyp.protech360.utils.GeofenceStruct;
import fyp.protech360.utils.Global;
import fyp.protech360.utils.ImageEncoderDecoder;


public class ConnectionDetails extends Fragment implements OnMapReadyCallback {

    View myView;
    MapView myMapView;
    GoogleMap myMap;
    String userID, username, image;
    MarkerOptions marker;
    String destinationCoordinates;
    Marker marker1;
    boolean firstTimeMapLoad;
    TextView userName, status;
    CircleImageView imageView;

    //FloatingActionButton dir;

    public ConnectionDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userID = getArguments().getString("User");
        username = getArguments().getString("Username");
        image = getArguments().getString("Image");

        ((Homepage) getActivity()).setActionBarTitle("Connection Details");

        firstTimeMapLoad = true;

        myView = inflater.inflate(fyp.protech360.R.layout.fragment_connection_details, container, false);

        userName = myView.findViewById(R.id.connection_name_field);
        status = myView.findViewById(R.id.connection_distance);
        imageView = myView.findViewById(R.id.connection_image);

        userName.setText(username);

        imageView.setImageBitmap(ImageEncoderDecoder.decodeImage(image));

        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //dir = myView.findViewById(R.id.getDirections);

        myMapView = myView.findViewById(R.id.map);

        if (myMapView != null) {
            myMapView.onCreate(null);
            myMapView.onResume();
            myMapView.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        myMap = googleMap;
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        myMap.setMyLocationEnabled(true);
        MapsInitializer.initialize(getActivity());

        marker = new MarkerOptions().position(new LatLng(50,6)).title(username);
        marker1 = myMap.addMarker(marker);

        updateRealtimeLocation();

//        dir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri gmmIntentUri = Uri.parse("google.navigation:q="+destinationCoordinates);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
//            }
//        });
    }

    public void updateRealtimeLocation(){

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Status").child(userID);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String loc = dataSnapshot.getValue(String.class);
                destinationCoordinates = loc;
                final String[] latLng = loc.split(",");

                final GeofenceStruct struct = new GeofenceStruct(userID);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Connections")
                        .child(Global.currentUser.getUuid())
                        .child(userID)
                        .child("requestRange");

                ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int range = Integer.parseInt((String.valueOf(dataSnapshot.getValue())));

                                struct.setRange(range);

                                struct.setLatitude(Double.parseDouble(latLng[0]));
                                struct.setLongitude(Double.parseDouble(latLng[1]));

                                int distance =
                                        (int) struct.getDistance(Global.user_latitude, Global.user_longitude);
                                String d = String.valueOf(distance);

                                if(distance < 1000)
                                    d = distance + "m";
                                else{
                                    double val = (double)distance / 1000;
                                    d = val + "km";
                                }

                                String stat = "";
                                Log.d("Usman_geofence_range", String.valueOf(range));
                                Boolean flag = distance > range;
                                stat = flag ? "Out of" : "In";

                                status.setText("Distance: " + d +
                                        "       Status: " + stat +
                                        " range");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                LatLng destination = new LatLng(Double.valueOf(latLng[0]),Double.valueOf(latLng[1]));
                marker1.setPosition(destination);
                if(firstTimeMapLoad){
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination,10));
                }
                firstTimeMapLoad = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}