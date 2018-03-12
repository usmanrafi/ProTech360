package fyp.protech360.Classes;

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

    public void addAdmin(User user){
        admins.add(user);
    }

    public void addUser(User user){
        members.add(user);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

}
