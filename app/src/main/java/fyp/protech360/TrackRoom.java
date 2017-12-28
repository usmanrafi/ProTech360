package fyp.protech360;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class TrackRoom extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Room> rooms = new ArrayList<>();
    RoomAdapter roomAdapter;
    FloatingActionButton fab;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        roomAdapter = new RoomAdapter(getActivity(),R.layout.track_room_row,rooms);
        myView = inflater.inflate(R.layout.track_room, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Track Rooms");

        fab = (FloatingActionButton) myView.findViewById(R.id.addRoom);
        listView = (ListView) myView.findViewById(R.id.roomsList);
        listView.setClickable(true);
        listView.setAdapter(roomAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(),TrackroomDetails.class));
            }
        });



        return myView;
    }


    @Override
    public void onResume() {
        rooms.clear();
        addList();
        super.onResume();
    }

    public void addList()
    {
        rooms.add(new Room("Room 1", null, null));
        rooms.add(new Room("Room 2", null, null));
        rooms.add(new Room("Room 3", null, null));
        rooms.add(new Room("Room 4", null, null));
        rooms.add(new Room("Room 5", null, null));
        rooms.add(new Room("Room 6", null, null));
        rooms.add(new Room("Room 7", null, null));
        rooms.add(new Room("Room 8", null, null));
        rooms.add(new Room("Room 9", null, null));
        rooms.add(new Room("Room 10", null, null));
        rooms.add(new Room("Room 11", null, null));
        rooms.add(new Room("Room 12", null, null));
        rooms.add(new Room("Room 13", null, null));
        rooms.add(new Room("Room 14", null, null));
    }



}
