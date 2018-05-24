package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import fyp.protech360.R;
import fyp.protech360.classes.Room;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;


public class AddTrackRoom extends Fragment {
    View myView;
    Button addRoom,cancel;
    ImageView addParticipants;
    EditText roomTitle;
    ArrayList<User> roomMembers;
    ArrayList<User> admins;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addtrackroom,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Create a TrackRoom");

        roomTitle = (EditText) myView.findViewById(R.id.roomName);
        addRoom = (Button) myView.findViewById(R.id.roomAddButton);
        cancel = (Button) myView.findViewById(R.id.roomCancelButton);
        addParticipants = (ImageView) myView.findViewById(R.id.addParticipants);

        roomMembers = new ArrayList<>();
        admins = new ArrayList<>();

        populateMembers();

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(roomMembers.size() == 0)
                {
                    Toast.makeText(getActivity(),"Add Participants before creating the room",Toast.LENGTH_LONG).show();
                }
                else
                {
                    addRoom.setClickable(false);
                    roomMembers.add(Global.currentUser);
                    Room trackRoom = new Room(roomTitle.getText().toString(),roomMembers,admins);
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Rooms").child(trackRoom.getUuid().toString());
                    dbRef.setValue(trackRoom).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(),"Track Room has been successfully created",Toast.LENGTH_SHORT).show();
                            ((Homepage) getActivity()).setFragment(new TrackRoom());
                        }
                    });
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Track Room Cancelled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new TrackRoom());
            }
        });

        addParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("Caller","TrackRoom");
                Fragment fragment = new SelectParticipant();
                fragment.setArguments(b);
                ((Homepage) getActivity()).setFragment(fragment);
            }
        });


        return myView;
    }

    public void populateMembers(){
        if(getArguments() != null){
            roomMembers = (ArrayList<User>) getArguments().getSerializable("Selected");
        }
        admins.add(Global.currentUser);
    }
}