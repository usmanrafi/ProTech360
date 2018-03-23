package fyp.protech360.classes;

import java.util.ArrayList;

public class Room {

    private String title;
    private ArrayList<User> members;
    private ArrayList<User> admins;

    public Room(){
        this.title = "Room";
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public Room(String title, ArrayList<User> members, ArrayList<User> admins){
        this.title = title;
        this.members = members;
        this.admins = admins;
    }

    public Room(String title, User admin){
        this.title = title;
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();

        this.admins.add(admin);
    }

    public void makeAdmin(User user){
        if(!(this.isAdmin(user)) && this.isMember(user)){
            this.admins.add(user);
            this.members.remove(user);
        }
    }

    public void addUser(User user){
        if(!(this.isMember(user)) && !(this.isAdmin(user)))
            this.members.add(user);
    }

    public boolean isAdmin(User user){
        for(User u : this.admins)
            if(user.equals(u))
                return true;

        return false;
    }

    public boolean isMember(User user){
        for(User u : this.members)
            if(user.equals(u))
                return true;

        return false;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }

    public ArrayList<User> getAdmins() {
        return this.admins;
    }
}
