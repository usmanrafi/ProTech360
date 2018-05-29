package fyp.protech360.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import fyp.protech360.classes.AlertDetail;
import fyp.protech360.R;

public class CustomAdapter extends ArrayAdapter implements Filterable {
        Activity activity;
        ArrayList<AlertDetail> alerts;
        ArrayList<AlertDetail> filteredAlerts;
        AlertHolder holder = new AlertHolder();
        int resource;

        public CustomAdapter(Activity activity, int resource,ArrayList<AlertDetail> alerts) {
            super(activity, resource, alerts);
            this.activity = activity;
            this.alerts = alerts;
            this.filteredAlerts = alerts;
            this.resource = resource;
        }

    @Override
    public int getCount() {
        try {
            return filteredAlerts.size();
        }catch(NullPointerException e){
            return 1;
        }
    }
    @Override
    public long getItemId(int position)
    {
        if(alerts == null)
            return position;
        return alerts.indexOf(filteredAlerts.get(position));

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

            holder.message = (TextView) convertView.findViewById(R.id.alertMessage);
            holder.date = (TextView) convertView.findViewById(R.id.alertTime);

            AlertDetail a = filteredAlerts.get(position);
            holder.message.setText(a.getMessage());

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(Long.parseLong(a.getTime()));
            int yy = c.get(Calendar.YEAR);
            int mm = c.get(Calendar.MONTH) + 1;
            int dd = c.get(Calendar.DAY_OF_MONTH);
            int hh = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);

            if (a.isToday(Long.parseLong(a.getTime()))) {
                holder.date.setText(hh + ":" + min);
            } else {
                holder.date.setText(dd+"/"+mm+"/"+yy);
            }


        return convertView;
    }


    static class AlertHolder{
        TextView message;
        TextView date;
    }

    @Override
    public android.widget.Filter getFilter(){
        return new android.widget.Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values

                ArrayList<AlertDetail> resultData = new ArrayList<AlertDetail>();
                if (constraint == null) {

                    // set the Original result to return
                    results.count = alerts.size();
                    results.values = alerts;

                }
                else{
                    String srch = constraint.toString().toUpperCase();
                    for (int i = 0; i < alerts.size(); i++) {
                        String data = alerts.get(i).getMessage();
                        if (data.toUpperCase().contains(srch)) {
                            resultData.add(alerts.get(i));
                        }
                    }
                    results.count = resultData.size();
                    results.values = resultData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredAlerts = (ArrayList<AlertDetail>) results.values;
                notifyDataSetChanged();
            }

        };
    }



}
