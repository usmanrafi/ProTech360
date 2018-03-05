package fyp.protech360;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



public class TimeBasedProfiles extends Fragment{

    View myView;
    Spinner spinner;

    Button submit;
    AudioManager am;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_time_based_profiles, container, false);
        //        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");

        spinner = myView.findViewById(R.id.profile_spinner);
        submit = myView.findViewById(R.id.submitTimeBasedProfiles);

        if(spinner != null){
            String[] list = getResources().getStringArray(R.array.spinner_profiles);
            spinner.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.layout_spinner,R.id.spinner_txt,list));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.setRingerMode(0);
            }
        });
        return myView;
    }

}
