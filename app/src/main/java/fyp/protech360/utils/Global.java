package fyp.protech360.utils;

// A class to cater global variables

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import java.util.ArrayList;

import fyp.protech360.classes.User;
import fyp.protech360.dal.DatabaseHelper;
import fyp.protech360.services.LocationService;
import fyp.protech360.ui.Home;
import fyp.protech360.ui.Homepage;

public class Global {

    public static User currentUser;

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

    static {
        timeBasedReminderID = 0;
        locationBasedReminderID = 0;
        timeBasedReminderReceiverID = 0;
        locationBasedReminderReceiverID = 0;

        LocationIntent = null;
    }

    public static void setLocationIntent(Context context){
        if(LocationIntent != null)
            LocationIntent = new Intent(context, LocationService.class);
    }
}
