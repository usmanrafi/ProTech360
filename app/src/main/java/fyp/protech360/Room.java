package fyp.protech360;

import java.util.ArrayList;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class Room {

    private String Title;
    private ArrayList<User> members;
    private ArrayList<User> admins;

    Room(String Title, ArrayList<User> members, ArrayList<User> admins){
        this.Title = Title;
        this.members = members;
    }

    public void addAdmin(User user)
    {
        admins.add(user);
    }

    public void addUser(User user)
    {
        members.add(user);
    }

    String getTitle(){
        return Title;
    }

}
