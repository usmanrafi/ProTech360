package fyp.protech360.Utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.Classes.ReminderDetail;


public class ReminderAdapter extends ArrayAdapter {

    Activity activity;
    ArrayList<ReminderDetail> reminders;
    ReminderHolder reminderHolder = new ReminderHolder();
    int resource;


    public ReminderAdapter(Activity activity, int resource, ArrayList<ReminderDetail> reminders) {
        super(activity, resource, reminders);
        this.activity = activity;
        this.reminders = reminders;
        this.resource = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

        reminderHolder.txt = (TextView) convertView.findViewById(R.id.reminderTitle);

        ReminderDetail rem = reminders.get(position);
        reminderHolder.txt.setText(rem.getText());

        return convertView;
    }




    static class ReminderHolder{
        TextView txt;
    }


}
