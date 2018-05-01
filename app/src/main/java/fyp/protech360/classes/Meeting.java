package fyp.protech360.classes;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Meeting implements Serializable{

    private String uuid;
    private Long time;
    private String name;
    private ArrayList<User> participants;
    private String location;

    public Meeting(){}

    public Meeting(String name, Long time, ArrayList<User> participants, String location) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
        this.participants = participants;
        this.location = location;
    }

    public void addParticipant(User user){
            this.participants.add(user);
    }

    public boolean isParticipant(User user){
        for(User u : this.participants)
            if(user.getUuid().equals(u.getUuid()))
                return true;

        return false;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
