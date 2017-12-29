package fyp.protech360;


import android.*;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;


public class connectionDetails extends Fragment implements OnMapReadyCallback{

    View myView;
    MapView myMapView;
    GoogleMap myMap;

    FloatingActionButton dir;

    public connectionDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_connection_details, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dir = myView.findViewById(R.id.getDirections);

        myMapView = myView.findViewById(R.id.map);

        if (myMapView != null) {
            myMapView.onCreate(null);
            myMapView.onResume();
            myMapView.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng origin, destination;

        destination = new LatLng(31.5204, 74.3587);

        MapsInitializer.initialize(getActivity());

        myMap = googleMap;
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        myMap.setMyLocationEnabled(true);

        myMap.addMarker(new MarkerOptions().position(destination).title("Sajjad"));


        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.5204,74.3587),10));


        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMap.addPolyline(new PolylineOptions()
                .add(new LatLng(31.4820022,74.3025007))
                .add(new LatLng(31.4834161,74.3063229))
                .add(new LatLng(31.4867392,74.30468379999999))
                .add(new LatLng(31.4871349,74.30483529999999))
                .add(new LatLng(31.5002922,74.3171364))
                .add(new LatLng(31.502941,74.3262769))
                .add(new LatLng(31.5057629,74.3348426))
                .add(new LatLng(31.5101101, 74.3403089))
                .add(new LatLng(31.5114741,74.3410411))
                .add(new LatLng(31.5230457,74.3476297))
                .add(new LatLng(31.52297669999999,74.34786989999999))
                .add(new LatLng(31.5214012,74.34703619999999))
                .add(new LatLng(31.5209553,74.3510512))
                .add(new LatLng(31.5232865,74.3550096))
                .add(new LatLng(31.520362,74.35872239999999)).color(Color.BLUE));
            }
        });
    }

}
