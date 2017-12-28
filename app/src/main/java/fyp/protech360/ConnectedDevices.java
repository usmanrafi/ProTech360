package fyp.protech360;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ConnectedDevices extends Fragment{
    View myView;
    ListView listView;
    CustomAdapter deviceAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new CustomAdapter(getActivity(),R.layout.connectionslist_row,null);
        myView = inflater.inflate(R.layout.connected_devices,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Devices");
        listView = (ListView) myView.findViewById(R.id.connectionsList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);



        return myView;
    }


}
