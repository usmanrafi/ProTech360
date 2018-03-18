package fyp.protech360.utils;

// A class to cater global variables

import java.util.ArrayList;

import fyp.protech360.classes.User;

public class Global {

    public static User currentUser;

    // ID to uniquely identify reminder alarms
    public static int timeBasedReminderID;
    public static int timeBasedReminderReceiverID;
    public static ArrayList<String> reminderTitles;

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


    static {
        timeBasedReminderID = 0;
        timeBasedReminderReceiverID = 0;
        reminderTitles = new ArrayList<>();
    }
}
