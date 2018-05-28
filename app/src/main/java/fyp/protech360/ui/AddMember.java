package fyp.protech360.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

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

/**
 * Created by Aliyan on 5/28/2018.
 */

public class AddMember extends Fragment {

    View myView;
    ListView listView;
    ProgressBar pb;
    ArrayList<User> connections = new ArrayList<>();
    ArrayList<User> selected = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    Button b;
    String roomId;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.select_participant_row,connections);
        myView = inflater.inflate(R.layout.select_participants,container,false);

        Bundle bundle = getArguments();
        connections.addAll((ArrayList<User>) bundle.getSerializable("List"));
        deviceAdapter.notifyDataSetChanged();
        roomId = bundle.getString("Room");
        selected = (ArrayList<User>) bundle.getSerializable("Connections");

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
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Rooms").child(roomId).child("Members");
                dbRef.setValue(selected);


            }
        });

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



}
