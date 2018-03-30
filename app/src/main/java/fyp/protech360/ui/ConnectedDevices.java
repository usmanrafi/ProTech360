package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.User;
import fyp.protech360.utils.ConnectionAdapter;

public class ConnectedDevices extends android.support.v4.app.Fragment {
    View myView;
    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row,connections);
        myView = inflater.inflate(R.layout.connected_devices,container,false);
//        ((Homepage) getActivity()).setActionBarTitle("Devices");
        fab = (FloatingActionButton) myView.findViewById(R.id.addConnection);
        listView = (ListView) myView.findViewById(R.id.connectionsList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Homepage) getActivity()).setFragment(new ConnectionDetails());
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

        //connections.add(new User("Asharib Nadeem","7-12-2017","08:11",null));
        //connections.add(new User("Haroon Ahmed","7-12-2017","06:06",null));
        //connections.add(new User("Kashif Ahmed","7-12-2017","00:56",null));
        //connections.add(new User("Osama Haroon","6-12-2017","00:10",null));
        //connections.add(new User("Saad Mujeeb","4-12-2017","23:22",null));
        //connections.add(new User("Syed Sajjad Ali","4-12-2017","19:45",null));
        //connections.add(new User("Syeda Zainab Bukhari","4-12-2017","06:04",null));
        //connections.add(new User("Tahir Ali","3-12-2017","03:58",null));
        //connections.add(new User("Usama Aslam","29-11-2017","18:11",null));
        //connections.add(new User("Usman Mohammad Rafi","27-11-2017","15:11",null));
        //connections.add(new User("Zainab Saif","25-11-2017","08:59",null));
    }

}
