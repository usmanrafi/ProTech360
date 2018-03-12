package fyp.protech360.UI;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fyp.protech360.R;


public class LocationBasedProfiles extends Fragment implements OnMapReadyCallback {

    View myView;
    MapView myMapView;
    GoogleMap myMap;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_location_based_profiles, container, false);
//        ((TrackroomDetails) getActivity()).setActionBarTitle("TrackRoom");

        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myMapView = myView.findViewById(R.id.map);
        spinner = myView.findViewById(R.id.profile_spinner);

        if (myMapView != null) {
            myMapView.onCreate(null);
            myMapView.onResume();
            myMapView.getMapAsync(this);
        }

        if(spinner != null){
            String[] list = getResources().getStringArray(R.array.spinner_profiles);
            spinner.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.layout_spinner,R.id.spinner_txt,list));
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
