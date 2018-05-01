package fyp.protech360.ui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.Schedule;
import fyp.protech360.utils.MeetingAdapter;

public class Meetings extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Schedule> meetings = new ArrayList<>();
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
                startActivity(new Intent(getActivity(),MeetingDetails.class));
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
        meetings.add(new Schedule("Meeting 1","7-1-2018","08:11","e",null));
        meetings.add(new Schedule("Meeting 2","7-1-2018","06:06","e",null));
        meetings.add(new Schedule("Meeting 3","7-1-2018","00:56","e",null));
        meetings.add(new Schedule("Meeting 4","6-1-2018","00:10","e",null));
        meetings.add(new Schedule("Meeting 5","4-1-2018","23:22","e",null));
        meetings.add(new Schedule("Meeting 6","4-1-2018","19:45","e",null));
        meetings.add(new Schedule("Meeting 7","4-1-2018","06:04","e",null));
        meetings.add(new Schedule("Meeting 8","3-1-2018","03:58","e",null));
        meetings.add(new Schedule("Meeting 9","29-1-2018","18:11","e",null));
        meetings.add(new Schedule("Meeting 10","27-2-2018","15:11","e",null));
        meetings.add(new Schedule("Meeting 11","25-4-2018","08:59","e",null));
    }


}