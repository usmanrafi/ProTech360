package fyp.protech360.UI;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fyp.protech360.Classes.ReminderDetail;
import fyp.protech360.R;
import fyp.protech360.UI.Homepage;
import fyp.protech360.Utils.ReminderAdapter;


public class Reminders extends Fragment {
    View myView;
    ListView listView;
    ArrayList<ReminderDetail> reminders = new ArrayList<>();
    ReminderAdapter reminderAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reminderAdapter = new ReminderAdapter(getActivity(), R.layout.reminder_row,reminders);
        myView = inflater.inflate(R.layout.fragment_reminders, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Reminders");

        listView = (ListView) myView.findViewById(R.id.remindersList);
        listView.setClickable(true);
        listView.setAdapter(reminderAdapter);


        return myView;
    }



    @Override
    public void onResume() {
        reminders.clear();
        addList();
        super.onResume();
    }

    public void addList()
    {
        reminders.add(new ReminderDetail("Play Game"));
        reminders.add(new ReminderDetail("Talk to Sir Aamir"));
        reminders.add(new ReminderDetail("Bring Yogurt"));
        reminders.add(new ReminderDetail("FYP Presentation"));
        reminders.add(new ReminderDetail("CA Exam"));
        reminders.add(new ReminderDetail("Deliver the post to the Community Club"));
        reminders.add(new ReminderDetail("Bring Chocolates"));
        reminders.add(new ReminderDetail("Ahmed's Birthday"));
        reminders.add(new ReminderDetail("Bonfire"));
        reminders.add(new ReminderDetail("Go to Sir Farooq's office"));
    }

}
