package fyp.protech360.utils;

import java.lang.Math;

/**
 * Created by Usman Rafi on 5/29/2018.
 */

public class GeofenceStruct {

    private String uuid;
    private int range;
    private double latitude;
    private double longitude;

    public GeofenceStruct(String uuid){
        this.uuid = uuid;
    }

    public GeofenceStruct(String uuid, int range, double latitude, double longitude) {
        this.uuid = uuid;
        this.range = range;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // this function receives the current user's latLng
    public boolean outOfBounds(double lat1, double long1){
        if(range > 0)
            if(getDistance(lat1, long1) > range)
                return true;

        return false;
    }

    public double getDistance(double lat1, double long1){
        double distance;

        double R = 6371000; // metres


        double rad_lat1 = Math.toRadians(lat1);
        double rad_lat2 = Math.toRadians(latitude);
        double delta_lat = Math.toRadians(latitude-lat1);
        double delta_long = Math.toRadians(longitude-long1);

        double a = Math.sin(delta_lat/2) * Math.sin(delta_lat/2) +
                Math.cos(rad_lat1) * Math.cos(rad_lat2) *
                        Math.sin(delta_long/2) * Math.sin(delta_long/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        distance = R * c;

        return distance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}


