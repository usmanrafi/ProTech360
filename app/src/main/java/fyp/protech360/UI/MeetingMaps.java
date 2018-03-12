package fyp.protech360.UI;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fyp.protech360.R;


public class MeetingMaps extends Fragment implements OnMapReadyCallback {

    View myView;
    MapView myMapView;
    GoogleMap myMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_meeting_map, container, false);
//        ((TrackroomDetails) getActivity()).setActionBarTitle("TrackRoom");

        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5214, 74.3577)).title("Zainab"));
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5224, 74.3597)).title("Haroon"));
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5234, 74.3547)).title("Osama"));
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5244, 74.3557)).title("Hussam"));
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5254, 74.3595)).title("Saad"));
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5264, 74.3544)).title("Ahmed"));
        myMap.addMarker(new MarkerOptions().position( new LatLng(31.5364, 74.3444)).title("Meeting Point")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.5204,74.3587),10));

    }

}
