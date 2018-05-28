package fyp.protech360.ui;

import android.app.Fragment;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.User;
import fyp.protech360.utils.ConnectionAdapter;
import fyp.protech360.utils.Global;

import static android.view.View.GONE;


public class SelectParticipant extends Fragment {
    View myView;
    ListView listView;
    ProgressBar pb;
    ArrayList<User> connections = new ArrayList<>();
    ArrayList<User> selected = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    Button b;
    String caller;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.select_participant_row,connections);
        myView = inflater.inflate(R.layout.select_participants,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Select Participants");


        caller = getArguments().getString("Caller");

        pb = myView.findViewById(R.id.progressSelection);

        pb.setVisibility(View.VISIBLE);

        listView = (ListView) myView.findViewById(R.id.addParticipantList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);

        b = (Button) myView.findViewById(R.id.done);
        b.setClickable(true);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < connections.size(); i++) {
                    if ((CheckBox) listView.getChildAt(i).findViewById(R.id.delete_check2) != null) {
                        CheckBox cb = listView.getChildAt(i).findViewById(R.id.delete_check2);
                        if (cb.isChecked()) {
                            selected.add((User) deviceAdapter.getItem(i));
                        }
                    }
                }

                Bundle b = new Bundle();
                b.putSerializable("Selected", selected);

                if (caller.equals("Meeting")) {
                    Fragment addMeeting = new AddMeeting();
                    addMeeting.setArguments(b);
                    ((Homepage) getActivity()).setFragment(addMeeting);
                }
                else if (caller.equals("Add Member"))
                {
                    Fragment addMeeting = new AddMeeting();
                    addMeeting.setArguments(b);
                    ((Homepage) getActivity()).setFragment(addMeeting);
                }
                else
                {
                    Fragment addRoom = new AddTrackRoom();
                    addRoom.setArguments(b);
                    ((Homepage) getActivity()).setFragment(addRoom);
                }

            }
        });

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
        //Add list of connections from FIREBASE here

        DatabaseReference conn = FirebaseDatabase.getInstance().getReference("Connections").child(Global.currentUser.getUuid());
        conn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    final String thisConn = ds.getKey();
                    DatabaseReference thisUser = FirebaseDatabase.getInstance().getReference("Users").child(thisConn);
                    thisUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            connections.add(user);
                            deviceAdapter.notifyDataSetChanged();
                            pb.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

