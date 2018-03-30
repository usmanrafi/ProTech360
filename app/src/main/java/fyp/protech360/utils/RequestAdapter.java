package fyp.protech360.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.ReminderDetail;
import fyp.protech360.classes.Request;


public class RequestAdapter extends ArrayAdapter {

    private Activity mActivity;
    private ArrayList<Request> requests;
    private int resource;
    RequestHolder requestHolder = new RequestHolder();

    public RequestAdapter(Activity activity, int requestslist_row, ArrayList<Request> requestees) {
        super(activity, requestslist_row, requestees);

        this.mActivity = activity;
        this.requests = requestees;
        this.resource = requestslist_row;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(resource, parent, false);
        }
        requestHolder.txt = (TextView) convertView.findViewById(R.id.requestTitle);

        Request rem = requests.get(position);
        requestHolder.txt.setText(rem.getRequestName());

        return convertView;
    }

    static class RequestHolder{
        TextView txt;
    }


}