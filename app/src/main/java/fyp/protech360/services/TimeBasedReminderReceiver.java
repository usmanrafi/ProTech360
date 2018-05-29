package fyp.protech360.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

import fyp.protech360.classes.AlertDetail;
import fyp.protech360.utils.Global;

public class TimeBasedReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences sharedPreferences = context.getSharedPreferences("TimeBasedReminders", Context.MODE_PRIVATE);

        String Notificationtitle = intent.getStringExtra("Notification_Title");

        String title = intent.getStringExtra("Content");

        String meetingID = intent.getStringExtra("MeetingID");

        Long num = Long.parseLong("10000000000000");

        DatabaseReference d = FirebaseDatabase.getInstance().getReference("Alerts").child(Global.currentUser.getUuid()).child(String.valueOf(num - Calendar.getInstance().getTimeInMillis()));
        AlertDetail a = new AlertDetail(title,String.valueOf(Calendar.getInstance().getTimeInMillis()));
        d.setValue(a);


        notificationManager.notify(Global.timeBasedReminderReceiverID++, new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_lock_lock)
                .setContentTitle("ProTech360 - " + Notificationtitle)
                .setContentText(title)
                .setTicker(Notificationtitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setAutoCancel(true)
                .build()
        );

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mp = MediaPlayer.create(context, alert);
        mp.setVolume(100,100);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(800);

        if(meetingID != null)
        {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Meetings").child(meetingID);
            dbRef.removeValue();
        }

    }
}
