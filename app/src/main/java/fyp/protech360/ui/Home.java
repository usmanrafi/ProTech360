package fyp.protech360.ui;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
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

import fyp.protech360.R;
import fyp.protech360.utils.Global;

import android.content.Context;

public class Home extends Fragment implements OnMapReadyCallback {

    GoogleMap myMap;
    MapView myMapView;
    View myView;

    Button panicButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Usman_Home","onCreateView of Home.java");
        myView = inflater.inflate(R.layout.home, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Home");

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener l = new LocationListener() {

            private Location lastLoc = null;

            @Override
            public void onLocationChanged(Location location) {
                
                Toast.makeText(getActivity(), "Speed: " + location.getSpeed() + " Altitude: " + location.getAltitude(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        lm.requestLocationUpdates("gps", 500, 10, l);


        panicButton = (Button) myView.findViewById(R.id.btn_panic);

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                myMap.setMyLocationEnabled(true);
                Location myLocation = myMap.getMyLocation();

                if (myLocation != null) {
                    String Lat = String.valueOf(myLocation.getLatitude());
                    String Long = String.valueOf(myLocation.getLongitude());
                    String Url = "http://www.google.com/maps/place/" + Lat + "," + Long;
//                    String messageToSend = "ALERT!!! I am in trouble, please help me.\nMy current location is: " + Url + ".\nPlease hurry up!\n\nSent via ProTech360";

                    String messageToSend = Global.currentUser.getEmergencyDetails().getMessage() +
                            "\nMy current location is: " + Url + ".\nPlease hurry up!\n\nSent via ProTech360";

                    String[] numbers = {Global.currentUser.getEmergencyDetails().getNum1(),Global.currentUser.getEmergencyDetails().getNum2(),Global.currentUser.getEmergencyDetails().getNum3()};

                    if (numbers[0] != null)
                        SmsManager.getDefault().sendTextMessage(numbers[0], null, messageToSend, null, null);

                    if (numbers[1] != null)
                        SmsManager.getDefault().sendTextMessage(numbers[1], null, messageToSend, null, null);

                    if (numbers[2] != null)
                        SmsManager.getDefault().sendTextMessage(numbers[2], null, messageToSend, null, null);

                }
                Toast.makeText(getActivity(), "Panic button pressed", Toast.LENGTH_SHORT).show();
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


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myMap.setMyLocationEnabled(true);

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.5204,74.3587),10));
    }



}
