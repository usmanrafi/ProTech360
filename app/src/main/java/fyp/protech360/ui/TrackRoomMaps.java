package fyp.protech360.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.Room;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;



public class TrackRoomMaps extends Fragment implements OnMapReadyCallback {

    View myView;
    MapView myMapView;
    GoogleMap myMap;
    ArrayList<Marker> markers = new ArrayList<>();
    ArrayList<Room> thisRoom = new ArrayList<>();
    Double latitude,longitude;
    boolean firstTimeMapLoad;
    int count;
    Room r;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_trackroom_map, container, false);
//        ((TrackroomDetails) getActivity()).setActionBarTitle("TrackRoom");

        firstTimeMapLoad = true;
        Bundle b = getArguments();
        thisRoom = (ArrayList<Room>) b.getSerializable("room");
        r = thisRoom.get(0);
        for(User u : thisRoom.get(0).getMembers())
        {
            Log.d("Pakistani",u.getName());
        }
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

        MapsInitializer.initialize(getActivity());

        myMap = googleMap;
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        myMap.setMyLocationEnabled(true);

        firstTimeMapLoad();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                respondToLocationChanges();
            }
        },10000);



    }

    private void respondToLocationChanges() {
        count = -1;
        for(int i = 0; i < r.getMembers().size(); i++)
        {
            final User thisUser = r.getMembers().get(i);
            if(!thisUser.equals(Global.currentUser))
                count++;

            DatabaseReference loc = FirebaseDatabase.getInstance().getReference("Status").child(thisUser.getUuid());
            loc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int num = getIndex(dataSnapshot.getKey());
                    String[] location = dataSnapshot.getValue().toString().split(",");
                    Double lat = Double.parseDouble(location[0]);
                    Double lng = Double.parseDouble(location[1]);
                    if(!thisUser.equals(Global.currentUser)){
                        markers.get(num).setPosition(new LatLng(lat,lng));
                    }
                    else{

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private int getIndex(String key) {
        ArrayList<User> users = r.getMembers();
        int count = 0;
        int result = 0;
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUuid().equals(Global.currentUser.getUuid())){}
            else {
                if (users.get(i).getUuid().equals(key))
                    result = count;
                else{
                    count++;
                }
            }
        }
        return result;
    }

    public void firstTimeMapLoad(){
        markers.clear();
        for(int i = 0; i < r.getMembers().size(); i++)
        {
            final User thisUser = r.getMembers().get(i);

            DatabaseReference loc = FirebaseDatabase.getInstance().getReference("Status").child(thisUser.getUuid());
            loc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MarkerOptions marker;
                    Log.d("Pak",dataSnapshot.toString());
                    String[] location = dataSnapshot.getValue().toString().split(",");
                    Double lat = Double.parseDouble(location[0]);
                    Double lng = Double.parseDouble(location[1]);
                    if(!thisUser.equals(Global.currentUser)){
                        marker =  new MarkerOptions().position(new LatLng(lat,lng)).title(thisUser.getName());
                        markers.add(myMap.addMarker(marker));
                    }
                    else{
                        latitude = lat;
                        longitude = lng;
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10));
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
