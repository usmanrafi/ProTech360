package fyp.protech360.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import fyp.protech360.R;
import fyp.protech360.services.LocationBasedProfileService;
import fyp.protech360.services.LocationBasedReminderService;
import fyp.protech360.utils.Global;

import static android.app.Activity.RESULT_OK;


public class LocationBasedReminders extends Fragment{

    View myView;
    EditText title;
    Button locationPicker;
    Place place;
    AlarmManager mAlarmManager;
    String rTitle;
    int PLACE_PICKER_REQUEST = 1;
    Button submit;
    private GeofencingClient geofencingClient;
    private static final int GEOFENCE_RADIUS = 50;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_location_based_reminders, container, false);

        title = myView.findViewById(R.id.reminderTitle);
        submit = myView.findViewById(R.id.submitLocationBasedReminder);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile();
            }
        });


        locationPicker = (Button) myView.findViewById(R.id.location_button);
        locationPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }

        });

        geofencingClient = LocationServices.getGeofencingClient(this.getActivity());


        return myView;
    }

    @SuppressLint("MissingPermission")
    private void setProfile() {
        rTitle = title.getText().toString();
        double lat = place.getLatLng().latitude;
        double lng = place.getLatLng().longitude;
        String key = ""+lat+"-"+lng;
        Geofence geofence = getGeofence(lat, lng, key);
        geofencingClient.addGeofences(getGeofencingRequest(geofence),
                getGeofencePendingIntent())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Profile set!", Toast.LENGTH_SHORT).show();
                        }else{
                        }
                    }
                });
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(getContext(), LocationBasedReminderService.class);
        intent.putExtra("Title",rTitle);
        return PendingIntent.getService(getContext(), Global.locationBasedReminderID++, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofence(geofence);
        return builder.build();
    }

    private Geofence getGeofence(double lat, double lang, String key) {
        return new Geofence.Builder()
                .setRequestId(key)
                .setCircularRegion(lat, lang, GEOFENCE_RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_DWELL)
                .setLoiteringDelay(10000)
                .build();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            place = PlacePicker.getPlace(getActivity(),data);
            Toast.makeText(getActivity(),String.valueOf(place.getLatLng().latitude),Toast.LENGTH_LONG).show();
        }
    }


}
