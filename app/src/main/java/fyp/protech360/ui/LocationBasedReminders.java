package fyp.protech360.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fyp.protech360.R;


public class LocationBasedReminders extends Fragment implements OnMapReadyCallback {

    View myView;
    MapView myMapView;
    GoogleMap myMap;
    Button b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_location_based_reminders, container, false);
//        ((TrackroomDetails) getActivity()).setActionBarTitle("TrackRoom");

        b = (Button) myView.findViewById(R.id.confirmRemLoc);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Reminder has successfully been set",Toast.LENGTH_SHORT).show();
            }

        });

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
        myMap.addMarker(new MarkerOptions().position(destination).title("Drag to set").draggable(true));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination,15));

    }

}
