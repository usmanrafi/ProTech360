package fyp.protech360.ui;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import fyp.protech360.R;


public class ConnectionDetails extends Fragment implements OnMapReadyCallback {

    View myView;
    MapView myMapView;
    GoogleMap myMap;
    String userID;
    MarkerOptions marker;
    String destinationCoordinates;
    Marker marker1;
    boolean firstTimeMapLoad;

    //FloatingActionButton dir;

    public ConnectionDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userID = getArguments().getString("User");
        firstTimeMapLoad = true;

        myView = inflater.inflate(fyp.protech360.R.layout.fragment_connection_details, container, false);
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

        marker = new MarkerOptions().position(new LatLng(50,6)).title(userID);
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
                String[] latLng = loc.split(",");
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