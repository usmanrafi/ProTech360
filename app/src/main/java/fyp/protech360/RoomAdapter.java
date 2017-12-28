package fyp.protech360;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class RoomAdapter extends ArrayAdapter {


    Activity activity;
    ArrayList<Room> rooms;
    RoomHolder roomHolder = new RoomHolder();
    int resource;


    public RoomAdapter(Activity activity, int resource, ArrayList<Room> rooms) {
        super(activity, resource, rooms);
        this.activity = activity;
        this.rooms = rooms;
        this.resource = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

        roomHolder.Title = (TextView) convertView.findViewById(R.id.roomTitle);
        roomHolder.numParticipants = (TextView) convertView.findViewById(R.id.roomCount);


        Room room = rooms.get(position);
        roomHolder.Title.setText(room.getTitle());

        roomHolder.numParticipants.setText("5 Participants");

        return convertView;
    }




    static class RoomHolder{
        TextView Title;
        TextView numParticipants;
    }

}
