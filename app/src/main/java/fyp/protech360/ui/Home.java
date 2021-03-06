package fyp.protech360.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Location;
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
import fyp.protech360.services.GeofenceService;
import fyp.protech360.services.LocationService;
import fyp.protech360.services.NotifyService;
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

        handleLocationService();
        handleOutOfRangeService();


        panicButton = (Button) myView.findViewById(R.id.btn_panic);

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                myMap.setMyLocationEnabled(true);
                Location myLocation = myMap.getMyLocation();

                if (myLocation != null) {
                    String Lat = String.valueOf(myLocation.getLatitude());
                    String Long = String.valueOf(myLocation.getLongitude());
                    String Url = "http://www.google.com/maps/place/" + Lat + "," + Long;
//
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
    public void onDestroyView() {
        handleLocationService();
        handleOutOfRangeService();
        handleMeetings();
        super.onDestroyView();
    }

    private void handleOutOfRangeService() {
        Log.d("Sajjad_Geofencing","Enters Function");
        if(Global.currentUser != null && !isMyGeoFenceServiceRunning(GeofenceService.class)) {
            Global.setGeofenceIntent(getActivity());
            Global.GeofenceIntent.putExtra("user_name", Global.currentUser);
            getActivity().startService(Global.GeofenceIntent);
        }
    }

    private boolean isMyGeoFenceServiceRunning(Class<GeofenceService> geofenceServiceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(geofenceServiceClass.getName().equals(service.service.getClassName())){

                return true;
            }
        }
        return false;
    }

    private void handleMeetings() {
            if(Global.currentUser != null && !isMyServiceRunning(NotifyService.class)) {
                Global.setMeetingIntent(getActivity());
                Global.MeetingIntent.putExtra("user_name", Global.currentUser);
                getActivity().startService(Global.MeetingIntent);
        }

    }

    private void handleLocationService() {
        if(Global.currentUser != null && !isMyLocationServiceRunning(LocationService.class)) {
            if(Global.currentUser.isTracking()) {
                Global.setLocationIntent(getActivity());
                Global.LocationIntent.putExtra("user_name", Global.currentUser.getUuid());
                getActivity().startService(Global.LocationIntent);
            }
        }

    }



    private boolean isMyLocationServiceRunning(Class<LocationService> locationServiceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(locationServiceClass.getName().equals(service.service.getClassName())){

                return true;
            }
        }
        return false;
    }

    private boolean isMyServiceRunning(Class<NotifyService> meetingNotifyServiceClass)
    {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(meetingNotifyServiceClass.getName().equals(service.service.getClassName())){

                return true;
            }
        }
        return false;
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
            return;
        }
        myMap.setMyLocationEnabled(true);

        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.5204,74.3587),10));
    }



}
