package fyp.protech360.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import fyp.protech360.R;
import fyp.protech360.services.TimeBasedReminderReceiver;
import fyp.protech360.utils.Global;


public class TimeBasedReminders extends Fragment{

    View myView;

    NumberPicker n1,n2,n3, n4, n5;
    private Button mButton;
    private EditText mEditText;     // Reminder Text

    private AlarmManager mAlarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_time_based_reminders, container, false);

        n1 = myView.findViewById(R.id.datePicker);
        n2 = myView.findViewById(R.id.monthPicker);
        n3 = myView.findViewById(R.id.yearPicker);
        n4 = myView.findViewById(R.id.hourPicker);
        n5 = myView.findViewById(R.id.minutePicker);
        mEditText = myView.findViewById(R.id.reminderTitle);
        mButton = myView.findViewById(R.id.confirmTimeBasedReminder);

        initializeValues();

        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("TimeBasedReminders",
                Context.MODE_PRIVATE).edit();

        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(
                        n3.getValue(),n2.getValue()-1,n1.getValue(),n4.getValue(),n5.getValue(),0
                    );

                Intent intent = new Intent(getActivity(), TimeBasedReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), Global.timeBasedReminderID++,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                String text = mEditText.getText().toString().trim();
                Long ms = calendar.getTimeInMillis();

                editor.putString("Time_"+String.valueOf(ms).substring(0,9), text);
                editor.commit();

                mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

                Toast.makeText(getContext(), "Alarm set!", Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
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
