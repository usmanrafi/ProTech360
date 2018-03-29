package fyp.protech360.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fyp.protech360.IBaseGPSListener;
import fyp.protech360.R;


public class LocationBasedProfileService extends IntentService {
        private static final String IDENTIFIER = "LocationAlertIS";

        public LocationBasedProfileService() {
            super(IDENTIFIER);
        }

        @Override
        protected void onHandleIntent(final Intent intent) {

            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    int code = intent.getIntExtra("Code",0);
                    notifyLocationAlert(code);
                }
            });
        }

        private String getGeofenceTransitionInfo(List<Geofence> triggeringGeofences) {
            ArrayList<String> locationNames = new ArrayList<>();
            for (Geofence geofence : triggeringGeofences) {
                locationNames.add(getLocationName(geofence.getRequestId()));
            }
            String triggeringLocationsString = TextUtils.join(", ", locationNames);

            return triggeringLocationsString;
        }

        private String getLocationName(String key) {
            String[] strs = key.split("-");

            String locationName = null;
            if (strs != null && strs.length == 2) {
                double lat = Double.parseDouble(strs[0]);
                double lng = Double.parseDouble(strs[1]);

                locationName = getLocationNameGeocoder(lat, lng);

            }
            if (locationName != null) {
                return locationName;
            } else {
                return key;
            }
        }

        private String getLocationNameGeocoder(double lat, double lng) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);
            } catch (Exception ioException) {
                Log.e("", "Error in getting location name for the location");
            }

            if (addresses == null || addresses.size() == 0) {
                Log.d("", "no location name");
                return null;
            } else {
                Address address = addresses.get(0);
                ArrayList<String> addressInfo = new ArrayList<>();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    addressInfo.add(address.getAddressLine(i));
                }

                return TextUtils.join(System.getProperty("line.separator"), addressInfo);
            }
        }

        private String getErrorString(int errorCode) {
            switch (errorCode) {
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    return "Geofence not available";
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    return "geofence too many_geofences";
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "geofence too many pending_intents";
                default:
                    return "geofence error";
            }
        }

        private String getTransitionString(int transitionType) {
            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    return "location entered";
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    return "location exited";
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    return "dwell at location";
                default:
                    return "location transition";
            }
        }

        private void notifyLocationAlert(int code) {

            Context context = getApplication();
            AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            switch (code){
                case 11:
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    break;
                case 10:
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case 21:
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case 20:
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    break;
                case 31:
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    break;
                case 30:
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case 41:
                    Toast.makeText(context,"Do Not Disturb ON",Toast.LENGTH_SHORT).show();
                    break;
                case 40:
                    Toast.makeText(context,"Do Not Disturb OFF",Toast.LENGTH_SHORT).show();
                    break;
                case 51:
                    Toast.makeText(context,"Do Not Track ON",Toast.LENGTH_SHORT).show();
                    break;
                case 50:
                    Toast.makeText(context,"Do Not Track OFF",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context,"Unknown",Toast.LENGTH_SHORT).show();
                    break;
            }

        }

}
