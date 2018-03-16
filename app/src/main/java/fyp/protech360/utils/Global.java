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

    static {
        timeBasedReminderID = 0;
        timeBasedReminderReceiverID = 0;
        reminderTitles = new ArrayList<>();
    }
}
