package fyp.protech360.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.classes.Room;
import fyp.protech360.classes.User;
import fyp.protech360.R;
import fyp.protech360.utils.ConnectionAdapter;


public class TrackRoomMembers extends Fragment{

    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    ArrayList<Room> rooms = new ArrayList<>();
    Room r;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row,connections);
        myView = inflater.inflate(R.layout.fragment_trackroom_members, container, false);
        listView = (ListView) myView.findViewById(R.id.roomPList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);



        return myView;
    }

    @Override
    public void onResume() {
        Log.d("Pak","Fragment Resumed");
        Bundle b = getArguments();
        rooms = (ArrayList<Room>) b.getSerializable("room");
        r = rooms.get(0);
        addList();
        super.onResume();
    }

    public void addList()
    {
        connections.clear();
        connections.addAll(r.getMembers());
        for(User u : connections){
            Log.d("Pakakaka",u.getName());
        }
        deviceAdapter.notifyDataSetChanged();
    }


}

