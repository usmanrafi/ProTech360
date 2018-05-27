package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import fyp.protech360.R;
import fyp.protech360.classes.Meeting;
import fyp.protech360.classes.User;
import fyp.protech360.utils.ConnectionAdapter;

/**
 * Created by Aliyan on 5/27/2018.
 */

public class MeetingDetails extends Fragment {

    View myView;
    String meetingID;
    Meeting meeting;
    TextView date, time;
    Button location, reminder, update, cancel;
    ListView participantsList;
    ConnectionAdapter participantsAdapter;
    ArrayList<User> participants = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(fyp.protech360.R.layout.fragment_meeting_details, container, false);

        meetingID = getArguments().getString("Meeting");

        date = myView.findViewById(R.id.meeting_date);
        time = myView.findViewById(R.id.meeting_time);
        location = myView.findViewById(R.id.meeting_location);
        reminder = myView.findViewById(R.id.meeting_reminder);
        update = myView.findViewById(R.id.meeting_update);
        cancel = myView.findViewById(R.id.meeting_cancel);
        participantsList = myView.findViewById(R.id.meeting_participants);

        participantsList.setClickable(true);

        participantsAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row, participants);
        participantsList.setAdapter(participantsAdapter);


        meetingStatus();

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Remind Participants
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Update Schedule
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Cancel Meeting
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Navigate to the meeting location
            }
        });

        return myView;
    }


    public void meetingStatus()
    {
        DatabaseReference meetingRef = FirebaseDatabase.getInstance().getReference("Meetings").child(meetingID);
        meetingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meeting = dataSnapshot.getValue(Meeting.class);
                participants.clear();
                participants.addAll(meeting.getParticipants());
                participantsAdapter.notifyDataSetChanged();
                addViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addViews() {

        ((Homepage) getActivity()).setActionBarTitle( meeting.getName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(meeting.getTime());
        String dayString = "", monthString = "", yearString = "", hourString = "", minuteString = "";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        dayString = String.valueOf(day);
        monthString = String.valueOf(month);
        yearString = String.valueOf(year);
        hourString = String.valueOf(hour);
        minuteString = String.valueOf(minute);

        if(day < 10) dayString = "0"+ dayString;
        if(month < 10) monthString = "0"+ monthString;
        if(hour < 10) hourString = "0"+ hourString;
        if(minute < 10) minuteString = "0"+ minuteString;

        date.setText(dayString + "/" + monthString + "/" + yearString);
        time.setText(hourString + ":" + minuteString);

    }


}
