package fyp.protech360.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import fyp.protech360.classes.Meeting;
import fyp.protech360.classes.Request;
import fyp.protech360.classes.Room;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;

/**
 * Created by Aliyan on 5/2/2018.
 */

public class NotifyService extends Service {

    User user;
    int serviceRunningFirstTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        user = (User) intent.getSerializableExtra("user_name");
        serviceRunningFirstTime = 4;

        handleRooms();
        handleMeetings();
        handleRequests();


        return START_REDELIVER_INTENT;
    }

    private void handleRooms() {
        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");
        roomsRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(serviceRunningFirstTime <= 0) {
                    Room r = dataSnapshot.getValue(Room.class);
                    if(!r.getAdmins().get(0).getUuid().equals(user.getUuid()) && r.isMember(user)) {
                        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                        ThisIntent.putExtra("Notification_Title", "Track Room");
                        ThisIntent.putExtra("Content", r.getAdmins().get(0).getName() + " has created a new room -" + r.getTitle() + "- . You were added to the room.");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);
                    }
                }
                else
                {
                    serviceRunningFirstTime--;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(serviceRunningFirstTime <= 0) {
                    Room r = dataSnapshot.getValue(Room.class);
                    if(r.isMember(user)) {
                        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                        ThisIntent.putExtra("Notification_Title", "Track Room");
                        ThisIntent.putExtra("Content", r.getTitle() + " room has been deleted.");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);
                    }
                }
                else
                {
                    serviceRunningFirstTime--;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference leaveRoomRef = FirebaseDatabase.getInstance().getReference("Leave");
        leaveRoomRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = (String) dataSnapshot.child("Name").getValue();
                String room = (String) dataSnapshot.child("Room").getValue();
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                ThisIntent.putExtra("Notification_Title", "Track Room");
                ThisIntent.putExtra("Content", name + " left the room " + room);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("AdminManipulation");
        adminRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = (String) dataSnapshot.child("Name").getValue();
                String room = (String) dataSnapshot.child("Room").getValue();
                String type = (String) dataSnapshot.child("Type").getValue();
                String userID = (String) dataSnapshot.child("User").getValue();
                if(user.getUuid().equals(userID)) {
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                    ThisIntent.putExtra("Notification_Title", "Track Room");
                    ThisIntent.putExtra("Content", name + " " + type + " of the room " + room);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference removeRef = FirebaseDatabase.getInstance().getReference("Remove");
        removeRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = (String) dataSnapshot.child("Name").getValue();
                String room = (String) dataSnapshot.child("Room").getValue();
                String username = (String) dataSnapshot.child("User_Name").getValue();
                String userID = (String) dataSnapshot.child("User").getValue();
                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                ThisIntent.putExtra("Notification_Title", "Track Room");
                if(user.getUuid().equals(userID))
                    ThisIntent.putExtra("Content", name + " removed you from the room " + room);
                else
                    ThisIntent.putExtra("Content", name + " removed " + username + " from the room " + room);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void handleRequests() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Requests");
        dbRef.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(serviceRunningFirstTime <= 0) {
                        if (dataSnapshot.getKey().equals(user.getUuid())) {
                            Request r = dataSnapshot.getValue(Request.class);
                            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                            ThisIntent.putExtra("Notification_Title", "Request");
                            ThisIntent.putExtra("Content", r.getRequestName() + "has sent you a connection request");
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);
                        }
                    }
                    else
                    {
                        serviceRunningFirstTime--;
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference reqResponse = FirebaseDatabase.getInstance().getReference("Connections").child(user.getUuid());
        reqResponse.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Request r = dataSnapshot.getValue(Request.class);
                if (serviceRunningFirstTime <= 0) {
                    if (r.getRequestName().equals(user.getEmail())) {
                        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                        ThisIntent.putExtra("Notification_Title", "Request");
                        ThisIntent.putExtra("Content", r.getSentTo() + " accepted your connection request.");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);

                    }
                }
                else
                {
                    serviceRunningFirstTime--;
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void handleMeetings() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Meetings");
        dbRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Meeting m = dataSnapshot.getValue(Meeting.class);
                if( serviceRunningFirstTime <= 0) {
                    if (m.isMember(user)) {
                        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                        ThisIntent.putExtra("Notification_Title", "Meeting");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(m.getTime());
                        ArrayList<User> users = m.getParticipants();
                        String participants = "";
                        for (User u : users) {
                            if (!u.getUuid().equals(user.getUuid())) {
                                participants += ", " + u.getName();
                            }
                        }
                        ThisIntent.putExtra("Content", "You have " + m.getName() + " meeting scheduled on " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " with " + participants);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);

                        AlarmManager alarmManager2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent ThisIntent2 = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                        ThisIntent2.putExtra("Notification_Title", "Meeting");
                        ThisIntent2.putExtra("MeetingID", m.getUuid());
                        ThisIntent2.putExtra("Content", "It's time for the " + m.getName() + " meeting to be started");
                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(String.valueOf(m.getTime()).substring(6, 12)), ThisIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager2.setExact(AlarmManager.RTC_WAKEUP, m.getTime(), pendingIntent2);
                    }
                }
                else
                {
                    serviceRunningFirstTime--;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Meeting m = dataSnapshot.getValue(Meeting.class);
                if(m.isMember(user) && !m.getName().equals("No Meeting") && !m.getUuid().equals("1")) {
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                    ThisIntent.putExtra("Notification_Title", "Meeting Reminder");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(m.getTime());
                    ArrayList<User> users = m.getParticipants();
                    String participants = "";
                    for (User u : users) {
                        if (!u.getUuid().equals(user.getUuid())) {
                            participants += ", " + u.getName();
                        }
                    }
                    ThisIntent.putExtra("Content", "Reminder: You have " + m.getName() + " meeting scheduled on " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " with " + participants);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Meeting m = dataSnapshot.getValue(Meeting.class);
                if(m.isMember(user) && m.getUuid().equals("1")) {
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    AlarmManager alarmManager2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                    Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                    ThisIntent.putExtra("Notification_Title", "Meeting");

                    ThisIntent.putExtra("Content", "The meeting " + m.getName() + " has been cancelled");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++, ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(String.valueOf(m.getTime()).substring(6,12)), ThisIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), pendingIntent);
                    alarmManager2.cancel(pendingIntent2);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
