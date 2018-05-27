package fyp.protech360.ui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.Meeting;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;
import fyp.protech360.utils.MeetingAdapter;

public class Meetings extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Meeting> meetings = new ArrayList<>();
    MeetingAdapter meetingAdapter;
    FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        meetingAdapter = new MeetingAdapter(getActivity(), R.layout.meeting_row,meetings);
        myView = inflater.inflate(R.layout.fragment_meetings, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Meetings");

        fab = (FloatingActionButton) myView.findViewById(R.id.addMeeting);
        listView = (ListView) myView.findViewById(R.id.meetingsList);
        listView.setClickable(true);
        listView.setAdapter(meetingAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meeting selectedMeeting = (Meeting) meetingAdapter.getItem(position);
                MeetingDetails meetingDetails = new MeetingDetails();
                Bundle args = new Bundle();
                args.putString("Meeting", selectedMeeting.getUuid());
                meetingDetails.setArguments(args);
                ((Homepage) getActivity()).setFragment(meetingDetails);

            }
        });



        return myView;
    }


    @Override
    public void onResume() {
        meetings.clear();
        meetingAdapter.notifyDataSetChanged();
        addList();
        super.onResume();
    }

    public void addList()
    {
        DatabaseReference roomList = FirebaseDatabase.getInstance().getReference("Meetings");
        roomList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meetings.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Meeting meeting = ds.getValue(Meeting.class);
                    if(meeting.isMember(Global.currentUser)) {
                        meetings.add(meeting);
                        meetingAdapter.notifyDataSetChanged();
                        listView.requestLayout();
                        //pb.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}