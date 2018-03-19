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

import fyp.protech360.utils.Global;

public class TimeBasedReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences sharedPreferences = context.getSharedPreferences("TimeBasedReminders", Context.MODE_PRIVATE);

        Long time = System.currentTimeMillis();
        String title = sharedPreferences.getString("Time_"+String.valueOf(time).substring(0,9), null);

        notificationManager.notify(Global.timeBasedReminderReceiverID++, new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_lock_lock)
                .setContentTitle("ProTech360")
                .setContentText(title)
                .setTicker("Reminder")
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
    }
}
