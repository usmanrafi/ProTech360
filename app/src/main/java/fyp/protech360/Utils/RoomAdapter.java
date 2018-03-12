package fyp.protech360.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fyp.protech360.Classes.Room;
import fyp.protech360.R;

public class RoomAdapter extends ArrayAdapter {


    private Activity activity;
    private ArrayList<Room> rooms;
    private RoomHolder roomHolder = new RoomHolder();
    private int resource;


    public RoomAdapter(Activity activity, int resource, ArrayList<Room> rooms) {
        super(activity, resource, rooms);
        this.activity = activity;
        this.rooms = rooms;
        this.resource = resource;
    }


    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);

        }

        roomHolder.Title = convertView.findViewById(R.id.roomTitle);
        roomHolder.numParticipants = convertView.findViewById(R.id.roomCount);


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
