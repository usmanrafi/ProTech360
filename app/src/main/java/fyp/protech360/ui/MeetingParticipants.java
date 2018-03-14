package fyp.protech360.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.User;
import fyp.protech360.utils.ConnectionAdapter;


public class MeetingParticipants extends Fragment{

    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ConnectionAdapter deviceAdapter;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row,connections);
        myView = inflater.inflate(R.layout.fragment_meeting_participants, container, false);
//        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");
        listView = (ListView) myView.findViewById(R.id.meetingPList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);



        return myView;
    }

    @Override
    public void onResume() {
        connections.clear();
        addList();
        super.onResume();
    }

    public void addList()
    {
        connections.add(new User("Asharib Nadeem","7-12-2017","08:11",null));
        connections.add(new User("Haroon Ahmed","7-12-2017","06:06",null));
        connections.add(new User("Kashif Ahmed","7-12-2017","00:56",null));
        connections.add(new User("Syed Sajjad Ali","4-12-2017","19:45",null));
        connections.add(new User("Zainab Saif","25-11-2017","08:59",null));
    }


}
