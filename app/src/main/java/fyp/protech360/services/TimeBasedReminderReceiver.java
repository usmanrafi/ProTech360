package fyp.protech360.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class TimeBasedReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Foobar!!!",Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setContentTitle("ProTech360")
                .setContentText("Reminder")
                ;
        notification.build();
    }
}
