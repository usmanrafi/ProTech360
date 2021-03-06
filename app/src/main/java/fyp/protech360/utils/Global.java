package fyp.protech360.utils;

// A class to cater global variables

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fyp.protech360.classes.User;
import fyp.protech360.dal.DatabaseHelper;
import fyp.protech360.services.GeofenceService;
import fyp.protech360.services.LocationService;
import fyp.protech360.services.NotifyService;

public class Global {

    public static User currentUser;

    public static double user_latitude;
    public static double user_longitude;


    public static DatabaseHelper dbHelper;

    // ID to uniquely identify reminder alarms
    public static int timeBasedReminderID;
    public static int timeBasedReminderReceiverID;
    public static int locationBasedReminderID;
    public static int locationBasedReminderReceiverID;

    // IDs to uniquely identify scheduled reminders;

    // Constants for TimeBasedProfile Settings
    public static final int PROFILE_SILENT_ON = 11;
    public static final int PROFILE_SILENT_OFF = 10;
    public static final int PROFILE_GENERAL_ON = 21;
    public static final int PROFILE_GENERAL_OFF = 20;
    public static final int PROFILE_MUTE_ON = 31;
    public static final int PROFILE_MUTE_OFF = 30;
    public static final int PROFILE_DONOTDISTURB_ON = 41;
    public static final int PROFILE_DONOTDISTURB_OFF = 40;
    public static final int PROFILE_DONOTTRACK_ON = 51;
    public static final int PROFILE_DONOTTRACK_OFF = 50;

    public static Intent LocationIntent;
    public static Intent MeetingIntent;
    public static Intent GeofenceIntent;

    static {
        timeBasedReminderID = 0;
        locationBasedReminderID = 0;
        timeBasedReminderReceiverID = 0;
        locationBasedReminderReceiverID = 0;

        LocationIntent = null;
        MeetingIntent = null;
        GeofenceIntent = null;
        dbHelper = null;

    }

    public static void initDB(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static void setLocationIntent(Context context){
        if(LocationIntent == null)
            LocationIntent = new Intent(context, LocationService.class);
    }

    public static void setMeetingIntent(Context context){
        if(MeetingIntent == null)
            MeetingIntent = new Intent(context, NotifyService.class);
    }

    public static void setGeofenceIntent(Context context){
        Log.d("Sajjad_Geofencing","Creating Intent");
        if(GeofenceIntent == null) {
            GeofenceIntent = new Intent(context, GeofenceService.class);
            Log.d("Sajjad_Geofencing","Intent Null");
        }
    }
}
