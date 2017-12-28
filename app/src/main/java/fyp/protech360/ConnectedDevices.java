package fyp.protech360;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ConnectedDevices extends Fragment{
    View myView;
    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(),R.layout.connectionslist_row,connections);
        myView = inflater.inflate(R.layout.connected_devices,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Devices");
        fab = (FloatingActionButton) myView.findViewById(R.id.addConnection);
        listView = (ListView) myView.findViewById(R.id.connectionsList);
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
