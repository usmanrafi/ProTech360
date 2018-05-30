package fyp.protech360.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fyp.protech360.classes.Room;
import fyp.protech360.classes.User;
import fyp.protech360.R;
import fyp.protech360.utils.ConnectionAdapter;
import fyp.protech360.utils.Global;


public class TrackRoomMembers extends Fragment{

    ListView listView;
    ArrayList<User> connections = new ArrayList<>();
    ArrayList<User> connectionsNotInRoom = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    ArrayList<Room> rooms = new ArrayList<>();
    Room r;
    LinearLayout addMembers;
    Button deleteRoom, leaveRoom;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row,connections);
        myView = inflater.inflate(R.layout.fragment_trackroom_members, container, false);
        listView = (ListView) myView.findViewById(R.id.roomPList);
        listView.setClickable(true);
        listView.setAdapter(deviceAdapter);

        addMembers = myView.findViewById(R.id.trackroom_members_Add);
        deleteRoom = myView.findViewById(R.id.deleteRoom);
        leaveRoom = myView.findViewById(R.id.leaveRoom);

        addMembers.setClickable(true);

        addMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddMember_Room.class);
                Bundle b = new Bundle();
                b.putSerializable("List",connectionsNotInRoom);
                b.putSerializable("Connections",connections);
                b.putString("Room",r.getUuid());
                intent.putExtra("Bundle",b);
                startActivity(intent);
                getActivity().finish();
            }
        });

        deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Delete Room");
                alert.setMessage("Are you sure you want to delete this Track Room?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        DatabaseReference deleteRoomRef = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid());
                        deleteRoomRef.removeValue();
                        Toast.makeText(getContext(),"Track Room: " + r.getTitle() + " deleted",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),Homepage.class);
                        intent.putExtra("GotoTrackRoom",true);

                        startActivity(intent);

                        getActivity().finish();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();

            }
        });

        leaveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Leave Room");
                alert.setMessage("Are you sure you want to leave this Track Room?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        connections.remove(Global.currentUser);
                        if(r.isAdmin(Global.currentUser))
                        {
                            r.removeAdmin(Global.currentUser);
                            if(connections.size() != 0 && r.getAdmins().size() == 0) {
                                r.makeAdmin(connections.get(0));
                            }
                        }


                        HashMap<String, Object> memberMap = new HashMap<>();
                        HashMap<String, Object> adminMap = new HashMap<>();

                        for(int i = 0; i < connections.size(); i++)
                        {
                            memberMap.put(String.valueOf(i),connections.get(i));
                        }
                        for(int i = 0; i < r.getAdmins().size(); i++)
                        {
                            adminMap.put(String.valueOf(i),r.getAdmins().get(i));
                        }


                        DatabaseReference memberRoomRef = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("members");
                        DatabaseReference adminRoomRef = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("admins");

                        memberRoomRef.removeValue();
                        adminRoomRef.removeValue();

                        memberRoomRef.updateChildren(memberMap);
                        adminRoomRef.updateChildren(adminMap);

                        DatabaseReference leaveRoomRef = FirebaseDatabase.getInstance().getReference("Leave").child(r.getUuid()+Global.currentUser.getUuid());
                        HashMap<String, Object> leaveMap = new HashMap<>();
                        leaveMap.put("Name",Global.currentUser.getName());
                        leaveMap.put("Room",r);
                        leaveRoomRef.setValue(leaveMap);

                        Toast.makeText(getContext(),"You are no longer a member of " + r.getTitle(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),Homepage.class);

                        leaveRoomRef.removeValue();

                        intent.putExtra("GotoTrackRoom", true);

                        startActivity(intent);
                        getActivity().finish();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence optionsForAdmin[] = {"Remove Participant", "Make Room Admin"};
                CharSequence optionsForAdminOnAdmin[] = {"Remove Participant", "Remove Room Admin"};

                if(Global.currentUser.equals(connections.get(position))){}
                else if(r.isAdmin(connections.get(position)) && r.isAdmin(Global.currentUser))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(optionsForAdminOnAdmin, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which) {
                                case 0:

                                    DatabaseReference removeRoomRef = FirebaseDatabase.getInstance().getReference("Remove").child(r.getUuid()+Global.currentUser.getUuid());
                                    HashMap<String, Object> removeMap = new HashMap<>();
                                    removeMap.put("Name",Global.currentUser.getName());
                                    removeMap.put("Room",r);
                                    removeMap.put("User_Name", ((User) deviceAdapter.getItem(position)).getName());
                                    removeMap.put("User", ((User) deviceAdapter.getItem(position)).getUuid());
                                    removeRoomRef.setValue(removeMap);


                                    r.getMembers().remove((User) deviceAdapter.getItem(position));
                                    r.removeAdmin((User) deviceAdapter.getItem(position));
                                    connections.remove(position);
                                    deviceAdapter.notifyDataSetChanged();
                                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("members");
                                    DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("admins");

                                    dbref.removeValue();

                                    HashMap<String,Object> map = new HashMap<>();
                                    HashMap<String,Object> map2 = new HashMap<>();
                                    for (int i = 0; i < connections.size(); i++)
                                    {
                                        map.put(String.valueOf(i),connections.get(i));
                                    }
                                    dbref.updateChildren(map);
                                    for(int i = 0; i < r.getAdmins().size(); i++)
                                    {
                                        map2.put(String.valueOf(i),r.getAdmins().get(i));
                                    }
                                    dbref2.updateChildren(map2);

                                    removeRoomRef.removeValue();

                                case 1:
                                    DatabaseReference dbrefAdmin = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("admins");
                                    r.removeAdmin((User) deviceAdapter.getItem(position));
                                    dbrefAdmin.removeValue();

                                    DatabaseReference adminRoomRef = FirebaseDatabase.getInstance().getReference("AdminManipulation").child(r.getUuid()+Global.currentUser.getUuid());
                                    HashMap<String, Object> adminMap = new HashMap<>();
                                    adminMap.put("Name",Global.currentUser.getName());
                                    adminMap.put("Room",r.getTitle());
                                    adminMap.put("Type", "removed you as the admin ");
                                    adminMap.put("User", ((User) deviceAdapter.getItem(position)).getUuid());
                                    adminRoomRef.setValue(adminMap);


                                    HashMap<String,Object> mapA = new HashMap<>();
                                    for (int i = 0; i < r.getAdmins().size(); i++)
                                    {
                                        mapA.put(String.valueOf(i),r.getAdmins().get(i));
                                    }
                                    dbrefAdmin.updateChildren(mapA);

                                    adminRoomRef.removeValue();

                                default:
                            }
                        }
                    });
                    builder.show();
                }
                else if(r.isAdmin(Global.currentUser))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(optionsForAdmin, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which) {
                                case 0:

                                    DatabaseReference removeRoomRef = FirebaseDatabase.getInstance().getReference("Remove").child(r.getUuid()+Global.currentUser.getUuid());
                                    HashMap<String, Object> removeMap = new HashMap<>();
                                    removeMap.put("Name",Global.currentUser.getName());
                                    removeMap.put("Room",r);
                                    removeMap.put("User_Name", ((User) deviceAdapter.getItem(position)).getName());
                                    removeMap.put("User", ((User) deviceAdapter.getItem(position)).getUuid());
                                    removeRoomRef.setValue(removeMap);


                                    r.getMembers().remove((User) deviceAdapter.getItem(position));
                                    connections.remove(position);
                                    deviceAdapter.notifyDataSetChanged();
                                    DatabaseReference dbrefRemove = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("members");
                                    dbrefRemove.removeValue();

                                    HashMap<String,Object> map = new HashMap<>();
                                    for (int i = 0; i < connections.size(); i++)
                                    {
                                        map.put(String.valueOf(i),connections.get(i));
                                    }
                                    dbrefRemove.updateChildren(map);

                                    removeRoomRef.removeValue();

                                case 1:
                                    DatabaseReference dbrefAdmin = FirebaseDatabase.getInstance().getReference("Rooms").child(r.getUuid()).child("admins");
                                    r.makeAdmin((User) deviceAdapter.getItem(position));
                                    dbrefAdmin.removeValue();

                                    DatabaseReference adminRoomRef = FirebaseDatabase.getInstance().getReference("AdminManipulation").child(r.getUuid()+Global.currentUser.getUuid());
                                    HashMap<String, Object> adminMap = new HashMap<>();
                                    adminMap.put("Name",Global.currentUser.getName());
                                    adminMap.put("Room",r.getTitle());
                                    adminMap.put("Type", "made you an admin ");
                                    adminMap.put("User", ((User) deviceAdapter.getItem(position)).getUuid());
                                    adminRoomRef.setValue(adminMap);


                                    HashMap<String,Object> mapA = new HashMap<>();
                                    for (int i = 0; i < r.getAdmins().size(); i++)
                                    {
                                        mapA.put(String.valueOf(i),r.getAdmins().get(i));
                                    }
                                    dbrefAdmin.updateChildren(mapA);

                                    adminRoomRef.removeValue();


                                default:
                            }
                        }
                    });
                    builder.show();                }
                else
                {

                }

            }
        });

        return myView;
    }

    @Override
    public void onResume() {
        Bundle b = getArguments();
        rooms = (ArrayList<Room>) b.getSerializable("room");
        r = rooms.get(0);

        if(r.isAdmin(Global.currentUser))
        {
            addMembers.setVisibility(View.VISIBLE);
            deleteRoom.setVisibility(View.VISIBLE);
        }
        else
        {
            addMembers.setVisibility(View.GONE);
            deleteRoom.setVisibility(View.GONE);
        }

        addList();
        super.onResume();
    }

    public void addList()
    {
        connections.clear();
        connections.addAll(r.getMembers());
        connectionsNotInRoom = Global.currentUser.getConnections();
        Log.d("dsdsd",String.valueOf(Global.currentUser.getConnections().size()));

        ArrayList<User> diff = new ArrayList<>();
        for(User u : connectionsNotInRoom)
        {
            if(connections.contains(u))
            {
                diff.add(u);
            }
        }
        for(User u : diff)
        {
            connectionsNotInRoom.remove(u);
        }
      deviceAdapter.notifyDataSetChanged();



    }


}

