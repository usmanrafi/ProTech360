package fyp.protech360.Classes;

import java.util.ArrayList;

public class Schedule {

    private String subject;
    private String date;
    private String startTime;
    private String endTime;
    private ArrayList<User> users;

    public Schedule(String subject, String date, String startTime, String endTime,ArrayList<User> users){
        this.subject = subject;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.users = users;
    }

    public String getSubject(){
        return subject;
    }

    public String getDate(){
        return date;
    }

    public String getStartTime(){
        return startTime;
    }

}
