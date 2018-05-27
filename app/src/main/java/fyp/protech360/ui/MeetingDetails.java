package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aliyan on 5/27/2018.
 */

public class MeetingDetails extends Fragment {

    View myView;
    String meetingID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(fyp.protech360.R.layout.fragment_connection_details, container, false);

        meetingID = getArguments().getString("Meeting");

        ((Homepage) getActivity()).setActionBarTitle("Meetings");


        return myView;
    }


}
