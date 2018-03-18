package fyp.protech360.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import fyp.protech360.utils.Global;

public class TimeBasedReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent --> Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        notificationManager.notify(Global.timeBasedReminderReceiverID, new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_lock_lock)
                .setContentTitle("ProTech360")
                .setContentText(Global.reminderTitles.get(Global.timeBasedReminderReceiverID))
                .setTicker("Reminder")
                .setAutoCancel(true)
                .build()
        );

        Log.d("Usman_TimeBasedRem",Global.reminderTitles.get(Global.timeBasedReminderReceiverID));
        Global.timeBasedReminderReceiverID++;

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
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

    }
}
