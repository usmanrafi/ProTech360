package fyp.protech360.ui;

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
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import fyp.protech360.classes.Room;
import fyp.protech360.R;
import fyp.protech360.utils.Global;
import fyp.protech360.utils.RoomAdapter;

public class TrackRoom extends Fragment {

    View myView;
    ListView listView;
    ArrayList<Room> rooms = new ArrayList<>();
    RoomAdapter roomAdapter;
    FloatingActionButton fab;
    ProgressBar pb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        roomAdapter = new RoomAdapter(getActivity(), R.layout.track_room_row,rooms);
        myView = inflater.inflate(R.layout.track_room, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Track Rooms");

        pb = myView.findViewById(R.id.progressRooms);
        pb.setVisibility(View.VISIBLE);

        fab = (FloatingActionButton) myView.findViewById(R.id.addRoom);
        listView = (ListView) myView.findViewById(R.id.roomsList);
        listView.setClickable(true);
        listView.setAdapter(roomAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),TrackroomDetails.class);
                Room room = (Room) roomAdapter.getItem(position);
                intent.putExtra("Room",room.getUuid());
                startActivity(intent);
            }
        });



        return myView;
    }


    @Override
    public void onResume() {
        rooms.clear();
        pb.setVisibility(View.VISIBLE);
        roomAdapter.notifyDataSetChanged();
        addList();
        super.onResume();
    }

    public void addList()
    {

        DatabaseReference roomList = FirebaseDatabase.getInstance().getReference("Rooms");
        roomList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Room room = ds.getValue(Room.class);
                    if(room.isMember(Global.currentUser)) {
                        rooms.add(room);
                        roomAdapter.notifyDataSetChanged();
                        listView.requestLayout();
                        pb.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
