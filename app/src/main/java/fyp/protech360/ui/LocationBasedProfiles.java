package fyp.protech360.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import fyp.protech360.R;
import fyp.protech360.services.LocationBasedProfileService;
import fyp.protech360.utils.Global;

import static android.app.Activity.RESULT_OK;


public class LocationBasedProfiles extends Fragment {

    View myView;
    Spinner spinner;
    Button locationPicker;
    Place place;
    AlarmManager mAlarmManager;
    int PLACE_PICKER_REQUEST = 1;
    ToggleButton toggleButton;
    Button submit;
    private GeofencingClient geofencingClient;
    private static final int GEOFENCE_RADIUS = 50;
    //in milli seconds

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_location_based_profiles, container, false);

        locationPicker = (Button) myView.findViewById(R.id.location_button);
        submit = myView.findViewById(R.id.submitLocationBasedProfile);
        toggleButton = myView.findViewById(R.id.locationBasedProfilesToggle);
        spinner = myView.findViewById(R.id.profile_spinner);
        geofencingClient = LocationServices.getGeofencingClient(this.getActivity());


        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        if(spinner != null){
            String[] list = getResources().getStringArray(R.array.spinner_profiles);
            spinner.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.layout_spinner,R.id.spinner_txt,list));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile();
            }
        });


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


        return myView;
    }

    @SuppressLint("MissingPermission")
    private void setProfile() {

        int code = getSpinnerCode();

        double lat = place.getLatLng().latitude;
                double lng = place.getLatLng().longitude;
                String key = ""+lat+"-"+lng;
                Geofence geofence = getGeofence(lat, lng, key);
                geofencingClient.addGeofences(getGeofencingRequest(geofence),
                        getGeofencePendingIntent(code))
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

    private PendingIntent getGeofencePendingIntent(int code) {
        Intent intent = new Intent(getContext(), LocationBasedProfileService.class);
        intent.putExtra("Code",code);
        return PendingIntent.getService(getContext(), 0, intent,
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
                .setLoiteringDelay(2000)
                .build();
    }






    private int getSpinnerCode() {
        int code;

        String setting = (String) spinner.getSelectedItem();

        switch (setting){
            case "General":
                code = toggleButton.isChecked() ? Global.PROFILE_GENERAL_ON : Global.PROFILE_GENERAL_OFF;
                break;
            case "Silent":
                code = toggleButton.isChecked() ? Global.PROFILE_SILENT_ON: Global.PROFILE_SILENT_OFF;
                break;
            case "Mute":
                code = toggleButton.isChecked() ? Global.PROFILE_MUTE_ON: Global.PROFILE_MUTE_OFF;
                break;
            case "Do Not Track":
                code = toggleButton.isChecked() ? Global.PROFILE_DONOTTRACK_ON: Global.PROFILE_DONOTTRACK_OFF;
                break;
            case "Do Not Disturb":
                code = toggleButton.isChecked() ? Global.PROFILE_DONOTDISTURB_ON: Global.PROFILE_DONOTDISTURB_OFF;
                break;

            default:
                code = 0;
        }
        return code;

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
