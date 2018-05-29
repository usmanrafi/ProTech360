package fyp.protech360.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlertDetail {

    private String message;
    private String time;

    public AlertDetail(String message, String time)
    {
        this.message = message;
        this.time = time;
    }

    public AlertDetail(){}


    public boolean isToday(long t)
    {
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(t);
        Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        return (yy == d.get(Calendar.YEAR) && mm == d.get(Calendar.MONTH) && dd == d.get(Calendar.DAY_OF_MONTH));
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setTime(String time){ this.time = time; }

    public String getMessage()
    {
        return message;
    }

    public String getTime()
    {
        return time;
    }


}
