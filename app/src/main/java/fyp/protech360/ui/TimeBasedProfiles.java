package fyp.protech360.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.NumberPicker;
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
    Button submit;
    NumberPicker n1,n2,n3, n4, n5;

    AlarmManager mAlarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_time_based_profiles, container, false);
        //        ((TrackRoomDetails) getActivity()).setActionBarTitle("TrackRoom");

        n1 = myView.findViewById(R.id.numberPicker2);
        n2 = myView.findViewById(R.id.numberPicker3);
        n3 = myView.findViewById(R.id.numberPicker4);
        n4 = myView.findViewById(R.id.numberPicker6);
        n5 = myView.findViewById(R.id.numberPicker7);

        initializeValues();

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
        calendar.set(
                    n3.getValue(), n2.getValue()-1, n1.getValue(),n4.getValue(), n5.getValue(),0
            );


        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("TimeBasedProfiles",
                Context.MODE_PRIVATE).edit();

        Intent intent = new Intent(getActivity(), TimeBasedProfileReceiver.class);

        int code = getSpinnerCode();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), code,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Long ms = calendar.getTimeInMillis();
            editor.putInt("Prof_"+String.valueOf(ms).substring(0,9), code);
            editor.commit();
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, ms,pendingIntent);
        }

        Toast.makeText(getContext(), "Profile set!", Toast.LENGTH_SHORT).show();
    }

    public int getSpinnerCode() {
        int code;

        String setting = (String) spinner.getSelectedItem();

        switch (setting){
            case "General":
                code = toggleButton.isChecked() ? Global.PROFILE_GENERAL_ON : Global.PROFILE_GENERAL_OFF;
                Toast.makeText(getActivity(),"a",Toast.LENGTH_LONG).show();
                break;
            case "Silent":
                code = toggleButton.isChecked() ? Global.PROFILE_SILENT_ON: Global.PROFILE_SILENT_OFF;
                Toast.makeText(getActivity(),"aa",Toast.LENGTH_LONG).show();
                break;
            case "Mute":
                code = toggleButton.isChecked() ? Global.PROFILE_MUTE_ON: Global.PROFILE_MUTE_OFF;
                Toast.makeText(getActivity(),"aaa",Toast.LENGTH_LONG).show();
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

    private void initializeValues() {

        Calendar cal = Calendar.getInstance();
        n1.setMinValue(1);
        n2.setMinValue(1);
        n3.setMinValue(cal.get(Calendar.YEAR));
        n4.setMinValue(00);
        n5.setMinValue(00);
        n3.setMaxValue(2058);
        n1.setMaxValue(31);
        n2.setMaxValue(12);
        n4.setMaxValue(23);
        n5.setMaxValue(59);


        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == 1 || newVal == 3 || newVal == 5 || newVal == 7 || newVal == 8 || newVal == 10 || newVal == 12)
                    n1.setMaxValue(31);
                else if(newVal == 4 || newVal == 6 || newVal == 9 || newVal == 11)
                    n1.setMaxValue(30);
                else {
                    if(n3.getValue() % 4 == 0 )
                        n1.setMaxValue(29);
                    else
                        n1.setMaxValue(28);
                }
                n1.setValue(1);
            }
        });

        n3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {


            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal % 4 == 0 && n2.getValue() == 2)
                    n1.setMaxValue(29);
                else if(newVal % 4 != 0 && n2.getValue() == 2)
                    n1.setMaxValue(28);
            }
        });


        n1.setValue(cal.get(cal.DAY_OF_MONTH));
        n2.setValue(cal.get(cal.MONTH)+1);
        n3.setValue(cal.get(cal.YEAR));
        n4.setValue(cal.get(cal.HOUR_OF_DAY));
        n5.setValue(cal.get(cal.MINUTE));

        n1.setWrapSelectorWheel(true);
        n2.setWrapSelectorWheel(true);
        n3.setWrapSelectorWheel(true);
        n4.setWrapSelectorWheel(true);
        n5.setWrapSelectorWheel(true);



    }

}
