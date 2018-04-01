package fyp.protech360.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import fyp.protech360.R;
import fyp.protech360.classes.User;

import static android.util.Base64.DEFAULT;

public class ConnectionAdapter extends ArrayAdapter {

    Activity activity;
    ArrayList<User> users;
    DeviceHolder deviceHolder = new DeviceHolder();
    int resource;


    public ConnectionAdapter(Activity activity, int resource, ArrayList<User> users) {
        super(activity, resource, users);
        this.activity = activity;
        this.users = users;
        Log.d("Syed_Sajjad",String.valueOf(users.size()));
        this.resource = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

            if(resource == R.layout.select_participant_row){
                deviceHolder.box = (CheckBox) convertView.findViewById(R.id.delete_check2);
            }

            deviceHolder.image = (CircleImageView) convertView.findViewById(R.id.contactImageShow);
            deviceHolder.name = (TextView) convertView.findViewById(R.id.contactNameShow);

            User user = users.get(position);
            deviceHolder.name.setText(user.getName());
            if(user.getImage() != null) {
                //deviceHolder.image.setImageBitmap(user.getImage());

                try {
                    Bitmap decodedByte = ImageEncoderDecoder.decodeImage(user.getImage());
                    deviceHolder.image.setImageBitmap(decodedByte);
                }
                catch (NullPointerException ne){
                    deviceHolder.image.setImageResource(R.drawable.add);
                }
            }

        return convertView;
    }


    static class DeviceHolder{
        CircleImageView image;
        CheckBox box;
        TextView name;
    }


}
