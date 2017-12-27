package fyp.protech360;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlertDetail {

    private String message;
    private String date;
    private String time;

    public boolean isToday()
    {
        Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        String today = (new StringBuilder().append(dd).append("-").append(mm+1).append("-").append(yy)).toString();
        return date.compareTo(today) == 0;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setCurrentTime()
    {
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    }

    public String getMessage()
    {
        return message;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }

    AlertDetail(String message, String date, String time)
    {
        this.message = message;
        this.date = date;
        this.time = time;
    }

    AlertDetail(){}

}
