package fyp.protech360;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class SelectParticipant extends Fragment {
    View myView;
    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    Button b;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(),R.layout.select_participant_row,connections);
        myView = inflater.inflate(R.layout.select_participants,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Select Participants");
        listView = (ListView) myView.findViewById(R.id.addParticipantList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);

        b = (Button) myView.findViewById(R.id.done);
        b.setClickable(true);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Participants have been added",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new AddMeeting());
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
        connections.add(new User("Asharib Nadeem","7-12-2017","08:11","e",null));
        connections.add(new User("Haroon Ahmed","7-12-2017","06:06","e",null));
        connections.add(new User("Kashif Ahmed","7-12-2017","00:56","e",null));
        connections.add(new User("Osama Haroon","6-12-2017","00:10","e",null));
        connections.add(new User("Saad Mujeeb","4-12-2017","23:22","e",null));
        connections.add(new User("Syed Sajjad Ali","4-12-2017","19:45","e",null));
        connections.add(new User("Syeda Zainab Bukhari","4-12-2017","06:04","e",null));
        connections.add(new User("Tahir Ali","3-12-2017","03:58","e",null));
        connections.add(new User("Usama Aslam","29-11-2017","18:11","e",null));
        connections.add(new User("Usman Mohammad Rafi","27-11-2017","15:11","e",null));
        connections.add(new User("Zainab Saif","25-11-2017","08:59","e",null));
    }

}

