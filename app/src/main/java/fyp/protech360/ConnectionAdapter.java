package fyp.protech360;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by Aliyan on 12/28/2017.
 */

public class ConnectionAdapter extends ArrayAdapter {

    Activity activity;
    ArrayList<User> users;
    DeviceHolder deviceHolder = new DeviceHolder();
    int resource;


    public ConnectionAdapter(Activity activity, int resource, ArrayList<User> users) {
        super(activity, resource, users);
        this.activity = activity;
        this.users = users;
        this.resource = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

            deviceHolder.image = (CircleImageView) convertView.findViewById(R.id.contactImageShow);
            deviceHolder.name = (TextView) convertView.findViewById(R.id.contactNameShow);

            User user = users.get(position);
            deviceHolder.name.setText(user.getName());
            if(user.getImage() != null) {
                deviceHolder.image.setImageBitmap(user.getImage());
            }

        return convertView;
    }




    static class DeviceHolder{
        CircleImageView image;
        TextView name;
    }


}
