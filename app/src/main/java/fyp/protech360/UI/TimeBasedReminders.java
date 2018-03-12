package fyp.protech360.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fyp.protech360.R;


public class TimeBasedReminders extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_time_based_reminders, container, false);
//        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");

        return myView;
    }

}
