package fyp.protech360;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MeetingParticipants extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_meeting_participants, container, false);
//        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");

        return myView;
    }

}
