package fyp.protech360;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
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
import com.google.maps.GeoApiContext;

import java.util.concurrent.TimeUnit;


public class Home extends Fragment implements OnMapReadyCallback {

    GoogleMap myMap;
    MapView myMapView;
    View myView;

    Button panicButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Home");

        panicButton = (Button) myView.findViewById(R.id.btn_panic);

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myMap.setMyLocationEnabled(true);
                Location myLocation = myMap.getMyLocation();

                if(myLocation != null) {
                    String Lat = String.valueOf(myLocation.getLatitude());
                    String Long = String.valueOf(myLocation.getLongitude());
                    String Url = "http://www.google.com/maps/place/" + Lat + "," + Long;
                    String messageToSend = "ALERT!!! I am in trouble, please help me.\nMy current location is: " + Url + ".\nPlease hurry up!\n\nSent via ProTech360";

                    String number = "+923154144453";
                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);

                    number = "+923354091046";
                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);

                    number = "+923444996663";
                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);

                    number = "+923248988663";
                    SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
                }
                Toast.makeText(getActivity(),"Panic button pressed",Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myMapView = (MapView) myView.findViewById(R.id.map);

        if (myMapView != null) {
            myMapView.onCreate(null);
            myMapView.onResume();
            myMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity());

        myMap = googleMap;
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        myMap.setMyLocationEnabled(true);

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.5204,74.3587),10));
    }



}
