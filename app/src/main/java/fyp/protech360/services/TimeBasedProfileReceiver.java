package fyp.protech360.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

public class TimeBasedProfileReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        SharedPreferences sharedPreferences = context.getSharedPreferences("TimeBasedProfiles", Context.MODE_PRIVATE);

        Long time = System.currentTimeMillis();
        int code = sharedPreferences.getInt("Prof_"+time.toString().substring(0,9), 0);

        switch (code){
            case 11:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case 10:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case 21:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case 20:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case 31:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case 30:
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case 41:
                Toast.makeText(context,"Do Not Disturb ON",Toast.LENGTH_SHORT).show();
                break;
            case 40:
                Toast.makeText(context,"Do Not Disturb OFF",Toast.LENGTH_SHORT).show();
                break;
            case 51:
                Toast.makeText(context,"Do Not Track ON",Toast.LENGTH_SHORT).show();
                break;
            case 50:
                Toast.makeText(context,"Do Not Track OFF",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context,"Unknown",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
