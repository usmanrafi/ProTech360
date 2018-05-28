package fyp.protech360.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.User;
import fyp.protech360.utils.ConnectionAdapter;

/**
 * Created by Aliyan on 5/28/2018.
 */

public class AddMember_Room extends AppCompatActivity {

    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ArrayList<User> selected = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    Button b;
    String roomId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_participants);
        deviceAdapter = new ConnectionAdapter(this, R.layout.select_participant_row,connections);

        Bundle bundle = getIntent().getBundleExtra("Bundle");
        connections.addAll((ArrayList<User>) bundle.getSerializable("List"));
        deviceAdapter.notifyDataSetChanged();
        roomId = bundle.getString("Room");
        selected = (ArrayList<User>) bundle.getSerializable("Connections");

        listView = (ListView) findViewById(R.id.addParticipantList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);

        b = (Button) findViewById(R.id.done);
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
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Rooms").child(roomId).child("members");
                dbRef.setValue(selected);

                startActivity(new Intent(getApplication(),Homepage.class));
                finish();

            }
        });



    }
}
