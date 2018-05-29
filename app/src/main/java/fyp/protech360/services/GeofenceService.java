package fyp.protech360.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import fyp.protech360.classes.User;

/**
 * Created by Usman Rafi on 5/29/2018.
 */

public class GeofenceService extends Service {

    private User currentUser;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
