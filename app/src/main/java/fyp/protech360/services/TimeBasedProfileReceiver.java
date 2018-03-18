package fyp.protech360.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

public class TimeBasedProfileReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        int code = getResultCode();

        Toast.makeText(context,String.valueOf(code),Toast.LENGTH_SHORT).show();
        //get code that was used to generate alarm

//        switch (code){
//            case 11:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//                break;
//            case 10:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                break;
//            case 21:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                break;
//            case 20:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//                break;
//            case 31:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                break;
//            case 30:
//                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                break;
//            case 41:
//                Toast.makeText(context,"Do Not Disturb ON",Toast.LENGTH_SHORT).show();
//                break;
//            case 40:
//                Toast.makeText(context,"Do Not Disturb OFF",Toast.LENGTH_SHORT).show();
//                break;
//            case 51:
//                Toast.makeText(context,"Do Not Track ON",Toast.LENGTH_SHORT).show();
//                break;
//            case 50:
//                Toast.makeText(context,"Do Not Track OFF",Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                Toast.makeText(context,"Unknown",Toast.LENGTH_SHORT).show();
//                break;
//        }
    }
}
