package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import fyp.protech360.R;


public class AddTrackRoom extends Fragment {
    View myView;
    Button b1,b2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addtrackroom,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Create a TrackRoom");

        b1 = (Button) myView.findViewById(R.id.roomAddButton);
        b2 = (Button) myView.findViewById(R.id.roomCancelButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Track Room has been successfully created",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new TrackRoom());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Track Room Cancelled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new TrackRoom());
            }
        });



        return myView;
    }
}