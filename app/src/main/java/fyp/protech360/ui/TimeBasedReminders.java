package fyp.protech360.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import fyp.protech360.R;
import fyp.protech360.services.TimeBasedReminderReceiver;


public class TimeBasedReminders extends Fragment{

    View myView;

    private TimePicker mtimePicker;
    private DatePicker mDatePicker;
    private Button mButton;

    private AlarmManager mAlarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_time_based_reminders, container, false);

        mtimePicker = myView.findViewById(R.id.timePicker);
        mDatePicker = myView.findViewById(R.id.datePicker);
        mButton = myView.findViewById(R.id.confirmTimeBasedReminder);

        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(
                            mDatePicker.getYear(),
                            mDatePicker.getMonth(),
                            mDatePicker.getDayOfMonth(),
                            mtimePicker.getHour(),
                            mtimePicker.getMinute(),
                            0
                    );
                }
                else{
                    calendar.set(
                            mDatePicker.getYear(),
                            mDatePicker.getMonth(),
                            mDatePicker.getDayOfMonth(),
                            mtimePicker.getCurrentHour(),
                            mtimePicker.getCurrentMinute(),
                            0
                    );
                }

                Intent intent = new Intent(getActivity(), TimeBasedReminderReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);

                mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

                Toast.makeText(getContext(), "Alarm set!", Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }

}
