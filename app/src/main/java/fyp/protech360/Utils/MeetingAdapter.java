package fyp.protech360.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.Classes.Schedule;


public class MeetingAdapter extends ArrayAdapter {
    private Activity activity;
    private ArrayList<Schedule> meetings;
    private ScheduleHolder scheduleHolder = new ScheduleHolder();
    private int resource;


    public MeetingAdapter(Activity activity, int resource, ArrayList<Schedule> meetings) {
        super(activity, resource, meetings);
        this.activity = activity;
        this.meetings = meetings;
        this.resource = resource;
    }


    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

        scheduleHolder.subject = convertView.findViewById(R.id.meetingTitle);
        scheduleHolder.date = convertView.findViewById(R.id.meetingDate);
        scheduleHolder.time = convertView.findViewById(R.id.meetingTime);


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
