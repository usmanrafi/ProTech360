package fyp.protech360.UI;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import fyp.protech360.R;


public class TimeBasedProfiles extends Fragment{

    View myView;
    Spinner spinner;

    ToggleButton toggleButton;

    Button submit;
    AudioManager am;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_time_based_profiles, container, false);
        //        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");

        spinner = myView.findViewById(R.id.profile_spinner);
        submit = myView.findViewById(R.id.submitTimeBasedProfiles);
        toggleButton = myView.findViewById(R.id.timeBasedProfilesToggle);

        am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);


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

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    am.setRingerMode(0);
                else
                    am.setRingerMode(2);
            }
        });
        return myView;
    }

}
