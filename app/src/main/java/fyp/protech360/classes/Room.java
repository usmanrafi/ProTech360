package fyp.protech360.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Room implements Serializable{

    private String Roomid;

    private String title;
    private ArrayList<User> members;
    private ArrayList<User> admins;

    public Room(){
        this.Roomid = UUID.randomUUID().toString();
        this.title = "Room";
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public Room(String title, ArrayList<User> members, ArrayList<User> admins){
        this.Roomid = UUID.randomUUID().toString();
        this.title = title;
        this.members = members;
        this.admins = admins;
    }

    public Room(String title, User admin){
        this.Roomid = UUID.randomUUID().toString();
        this.title = title;
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();

        this.admins.add(admin);
    }

    public void makeAdmin(User user){
        this.admins.add(user);
    }

    public void removeAdmin(User user){
        this.admins.remove(user);
    }

    public void addUser(User user){
        this.members.add(user);
    }

    public boolean isAdmin(User user){
        for(User u : this.admins)
            if(user.getUuid().equals(u.getUuid()))
                return true;

        return false;
    }

    public boolean isMember(User user){
        for(User u : this.members)
            if(user.getUuid().equals(u.getUuid()))
                return true;

        return false;
    }

    public String getUuid() {
        return Roomid;
    }

    public void setUuid(String Roomid) {
        this.Roomid = Roomid;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public void setAdmins(ArrayList<User> admins) {
        this.admins = admins;
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }

    public ArrayList<User> getAdmins() {
        return this.admins;
    }
}
