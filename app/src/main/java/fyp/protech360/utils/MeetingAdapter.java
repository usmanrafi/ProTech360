package fyp.protech360.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import fyp.protech360.R;
import fyp.protech360.classes.Meeting;


public class MeetingAdapter extends ArrayAdapter {
    private Activity activity;
    private ArrayList<Meeting> meetings;
    private MeetingHolder MeetingHolder = new MeetingHolder();
    private int resource;


    public MeetingAdapter(Activity activity, int resource, ArrayList<Meeting> meetings) {
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

        MeetingHolder.subject = convertView.findViewById(R.id.meetingTitle);
        MeetingHolder.date = convertView.findViewById(R.id.meetingDate);


        Meeting meeting = meetings.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(meeting.getTime());
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" + String.valueOf(calendar.get(Calendar.MONTH )+1) + "-" + calendar.get(Calendar.YEAR);
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        MeetingHolder.subject.setText(meeting.getName());
        MeetingHolder.date.setText("Schedule: " + date + " at " + time);

        return convertView;
    }




    static class MeetingHolder{
        TextView subject;
        TextView date;

    }

}
