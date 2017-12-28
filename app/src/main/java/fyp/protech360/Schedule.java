package fyp.protech360;

import java.util.ArrayList;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class Schedule {

    private String subject;
    private String date;
    private String startTime;
    private String endTime;
    private ArrayList<User> users;

    Schedule(String subject, String date, String startTime, String endTime,ArrayList<User> users){
        this.subject = subject;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.users = users;
    }

    String getSubject(){
        return subject;
    }

    String getDate(){
        return date;
    }

    String getStartTime(){
        return startTime;
    }

}
