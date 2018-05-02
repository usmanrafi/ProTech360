package fyp.protech360.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import fyp.protech360.classes.Meeting;
import fyp.protech360.classes.Request;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;

/**
 * Created by Aliyan on 5/2/2018.
 */

public class NotifyService extends Service {

    User user;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        user = (User) intent.getSerializableExtra("user_name");

        handleMeetings();
        handleRequests();

        return START_REDELIVER_INTENT;
    }

    private void handleRequests() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Requests");
        dbRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.getKey().equals(user.getUuid())){
                        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                        ThisIntent.putExtra("Notification_Title","Request");
                        ThisIntent.putExtra("Content", "You have a new request");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++,ThisIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),pendingIntent);
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
                if(m.isMember(user))
                {
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent ThisIntent = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                    ThisIntent.putExtra("Notification_Title","Meeting");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(m.getTime());
                    ArrayList<User> users = m.getParticipants();
                    String participants = "";
                    for(User u : users)
                    {
                        if(!u.getUuid().equals(user.getUuid())){
                            participants += ", " + u.getName();
                        }
                    }
                    ThisIntent.putExtra("Content","You have " + m.getName() + " meeting scheduled on " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + String.valueOf(calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) + " at " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " with " + participants);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++,ThisIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),pendingIntent);

                    AlarmManager alarmManager2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent ThisIntent2 = new Intent(getApplicationContext(), TimeBasedReminderReceiver.class);
                    ThisIntent2.putExtra("Notification_Title","Meeting");
                    ThisIntent2.putExtra("MeetingID",m.getUuid());
                    ThisIntent2.putExtra("Content","It's time for the " + m.getName() + " meeting to be started");
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), Global.timeBasedReminderID++,ThisIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager2.setExact(AlarmManager.RTC_WAKEUP, m.getTime(),pendingIntent2);
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
}
