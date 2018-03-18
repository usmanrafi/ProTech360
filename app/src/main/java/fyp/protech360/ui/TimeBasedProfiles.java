package fyp.protech360.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

import fyp.protech360.R;
import fyp.protech360.services.TimeBasedProfileReceiver;
import fyp.protech360.services.TimeBasedReminderReceiver;
import fyp.protech360.utils.Global;


public class TimeBasedProfiles extends Fragment{

    View myView;
    Spinner spinner;

    ToggleButton toggleButton;

    TimePicker mTimePicker;
    DatePicker mDatePicker;
    Button submit;

    AlarmManager mAlarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_time_based_profiles, container, false);
        //        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");

        mDatePicker = myView.findViewById(R.id.datePicker);
        mTimePicker = myView.findViewById(R.id.timePicker);

        spinner = myView.findViewById(R.id.profile_spinner);
        submit = myView.findViewById(R.id.submitTimeBasedProfiles);
        toggleButton = myView.findViewById(R.id.timeBasedProfilesToggle);

        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        if(spinner != null){
            String[] list = getResources().getStringArray(R.array.spinner_profiles);
            spinner.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.layout_spinner,R.id.spinner_txt,list));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile();
            }
        });

        return myView;
    }

    private void setProfile() {
        Calendar calendar = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            calendar.set(
                    mDatePicker.getYear(),
                    mDatePicker.getMonth(),
                    mDatePicker.getDayOfMonth(),
                    mTimePicker.getHour(),
                    mTimePicker.getMinute(),
                    0
            );
        }
        else{
            calendar.set(
                    mDatePicker.getYear(),
                    mDatePicker.getMonth(),
                    mDatePicker.getDayOfMonth(),
                    mTimePicker.getCurrentHour(),
                    mTimePicker.getCurrentMinute(),
                    0
            );
        }

        Intent intent = new Intent(getActivity(), TimeBasedProfileReceiver.class);

        int code = getSpinnerCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), code,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        }

        Toast.makeText(getContext(), "Profile set!", Toast.LENGTH_SHORT).show();
    }

    public int getSpinnerCode() {
        int code;

        String setting = (String) spinner.getSelectedItem();

        switch (setting){
            case "General":
                code = toggleButton.isChecked() ? Global.PROFILE_GENERAL_ON : Global.PROFILE_GENERAL_OFF;
                break;
            case "Silent":
                code = toggleButton.isChecked() ? Global.PROFILE_SILENT_ON: Global.PROFILE_SILENT_OFF;
                break;
            case "Mute":
                code = toggleButton.isChecked() ? Global.PROFILE_MUTE_ON: Global.PROFILE_MUTE_OFF;
                break;
            case "Do Not Track":
                code = toggleButton.isChecked() ? Global.PROFILE_DONOTTRACK_ON: Global.PROFILE_DONOTTRACK_OFF;
                break;
            case "Do Not Disturb":
                code = toggleButton.isChecked() ? Global.PROFILE_DONOTDISTURB_ON: Global.PROFILE_DONOTDISTURB_OFF;
                break;

            default:
                code = 0;
        }
        return code;
    }
}
