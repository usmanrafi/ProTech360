package fyp.protech360.classes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Meeting {

    private String name;
    private ArrayList<User> participants;
    private ArrayList<User> admins;
    private LatLng location;

    public Meeting(String name, ArrayList<User> participants, ArrayList<User> admins, LatLng location) {
        this.name = name;
        this.participants = participants;
        this.admins = admins;
        this.location = location;
    }

    public void addParticipant(User user){
        this.participants.add(user);
    }

    public void makeAdmin(User user){
        this.participants.remove(user);
        this.admins.add(user);
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public ArrayList<User> getAdmins() {
        return admins;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
