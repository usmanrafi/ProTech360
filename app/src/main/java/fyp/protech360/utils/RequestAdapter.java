package fyp.protech360.utils;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import fyp.protech360.classes.Request;


public class RequestAdapter extends ArrayAdapter {

    private Activity mActivity;
    private ArrayList<Request> requests;
    private int resource;

    public RequestAdapter(Activity activity, int requestslist_row, ArrayList<Request> requestees) {
        super(activity, requestslist_row, requestees);

        this.mActivity = activity;
        this.requests = requestees;
        this.resource = requestslist_row;

    }
}
