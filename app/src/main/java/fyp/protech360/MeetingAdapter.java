package fyp.protech360;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class MeetingAdapter extends ArrayAdapter {
    Activity activity;
    ArrayList<Schedule> meetings;
    ScheduleHolder scheduleHolder = new ScheduleHolder();
    int resource;


    public MeetingAdapter(Activity activity, int resource, ArrayList<Schedule> meetings) {
        super(activity, resource, meetings);
        this.activity = activity;
        this.meetings = meetings;
        this.resource = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

        scheduleHolder.subject = (TextView) convertView.findViewById(R.id.meetingTitle);
        scheduleHolder.date = (TextView) convertView.findViewById(R.id.meetingDate);
        scheduleHolder.time = (TextView) convertView.findViewById(R.id.meetingTime);


        Schedule meeting = meetings.get(position);
        scheduleHolder.subject.setText(meeting.getSubject());
        scheduleHolder.date.setText(meeting.getDate());
        scheduleHolder.time.setText(meeting.getStartTime());

        return convertView;
    }




    static class ScheduleHolder{
        TextView subject;
        TextView date;
        TextView time;
    }

}
