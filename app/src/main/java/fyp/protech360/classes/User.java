package fyp.protech360.classes;

import android.graphics.Bitmap;

import java.util.ArrayList;


public class User {

    private String uuid;
    private String name;
    private String phoneNumber;
    private String email;
    private String image;

    private EmergencyDetails emergencyDetails;

    private ArrayList<Meeting> meetings;
    private ArrayList<Room> rooms;

    private ArrayList<User> connections;

    public User(){}

    public User(String uuid, String name, String phoneNumber, String email, String image)
    {
        this.uuid = uuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
        this.emergencyDetails = new EmergencyDetails("Help!","0","0","0");
        this.meetings = new ArrayList<>();
        this.rooms = new ArrayList<>();

        this.connections = new ArrayList<>();
    }


    public User(String uuid, String name, String phoneNumber, String email, String image, EmergencyDetails det)
    {
        this.uuid = uuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.image = image;
        this.emergencyDetails = det;

        this.meetings = new ArrayList<>();
        this.rooms = new ArrayList<>();

        this.connections = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        if(!(obj instanceof User))
            return false;

        if(((User) obj).getUuid().equals(this.uuid))
            return true;

        if(((User) obj).getEmail().equalsIgnoreCase(this.email))
            return true;

        if(((User) obj).getPhoneNumber().equals(this.phoneNumber))
            return true;

        return false;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImage(){
        return this.image;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public EmergencyDetails getEmergencyDetails(){return this.emergencyDetails;}

    public void setEmergencyDetails(EmergencyDetails det){
        this.emergencyDetails = det;
    }

    public ArrayList<Meeting> getMeetings() {
        return this.meetings;
    }

    public void setMeetings(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void initConnections(){
        this.connections = new ArrayList<>();
    }

    public void addConnection(User user){
        boolean alreadyExists = false;
         for (User u : connections)
                if (u.getUuid().equals(this.uuid))
                    alreadyExists = true;

            if (!alreadyExists)
                this.connections.add(user);

    }

    public ArrayList<User> getConnections() {
        return connections;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
