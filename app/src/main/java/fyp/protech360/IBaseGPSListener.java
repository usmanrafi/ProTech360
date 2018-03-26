package fyp.protech360;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public interface IBaseGPSListener extends LocationListener,GpsStatus.Listener {
    @Override
    public void onGpsStatusChanged(int event);

    @Override
    public void onLocationChanged(Location location);

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras);

    @Override
    public void onProviderEnabled(String provider);
    @Override
    public void onProviderDisabled(String provider);
}
