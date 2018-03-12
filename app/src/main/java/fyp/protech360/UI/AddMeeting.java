package fyp.protech360.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import fyp.protech360.R;

public class AddMeeting extends Fragment {
    View myView;
    Button b1,b2;
    ImageView iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addmeeting,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Schedule a Meeting");

        b1 = (Button) myView.findViewById(R.id.meetingAddButton);
        b2 = (Button) myView.findViewById(R.id.meetingCancelButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Meeting has been successfully scheduled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new Meetings());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Meeting Schedule Cancelled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new Meetings());
            }
        });

        iv = (ImageView) myView.findViewById(R.id.addParticipantsToMeeting);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Homepage) getActivity()).setFragment(new SelectParticipant());
            }
        });




        return myView;
    }
}