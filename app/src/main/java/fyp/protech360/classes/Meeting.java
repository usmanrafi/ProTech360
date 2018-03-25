package fyp.protech360.classes;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.UUID;

public class Meeting {

    private UUID uuid;

    private String name;
    private ArrayList<User> participants;
    private ArrayList<User> admins;
    private LatLng location;

    public Meeting(String name, ArrayList<User> participants, ArrayList<User> admins, LatLng location) {
        this.uuid = UUID.randomUUID();

        this.name = name;
        this.participants = participants;
        this.admins = admins;
        this.location = location;
    }

    public Meeting(String name, User admin, LatLng location){
        this.uuid = UUID.randomUUID();

        this.name = name;
        this.location = location;
        this.participants = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.admins.add(admin);
    }

    public void addParticipant(User user){
        if(!(this.isParticipant(user)) && !(this.isAdmin(user)))
            this.participants.add(user);
    }

    public void makeAdmin(User user){
        if(!(this.isAdmin(user)) && this.isParticipant(user)) {
            this.participants.remove(user);
            this.admins.add(user);
        }
    }

    public boolean isAdmin(User user){
        for(User u : this.admins)
            if(user.equals(u))
                return true;

        return false;
    }

    public boolean isParticipant(User user){
        for(User u : this.participants)
            if(user.equals(u))
                return true;

        return false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
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
