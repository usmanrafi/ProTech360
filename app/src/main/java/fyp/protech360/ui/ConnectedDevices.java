package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.Request;
import fyp.protech360.classes.User;
import fyp.protech360.utils.ConnectionAdapter;
import fyp.protech360.utils.Global;
import fyp.protech360.utils.RequestAdapter;

public class ConnectedDevices extends Fragment {
    View myView;

    //LinearLayout devicesView, requestsView;
    TabLayout mTabLayout;

    ListView devicesList, requestsList;
    ArrayList<User> connections = new ArrayList<>();
    ArrayList<Request> requestees = new ArrayList<>();
    ConnectionAdapter deviceAdapter;
    RequestAdapter requestAdapter;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.connected_devices,container,false);
//        ((Homepage) getActivity()).setActionBarTitle("Devices");

        deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row,connections);
        requestAdapter = new RequestAdapter(getActivity(), R.layout.requests_row,requestees);

        mTabLayout = myView.findViewById(R.id.tab);

        fab =  myView.findViewById(R.id.addConnection);
        devicesList = myView.findViewById(R.id.devicesList);
        devicesList.setClickable(true);
        devicesList.setAdapter(deviceAdapter);

        devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Homepage) getActivity()).setFragment(new ConnectionDetails());
            }
        });


        requestsList = myView.findViewById(R.id.requestsList);
        requestsList.setClickable(true);
        requestsList.setAdapter(requestAdapter);
//
        requestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open dialog to confirm
                // inside Dialog ->
//                    boolean Requestchoice = true;
//
//                    if(Requestchoice){
//
//                        //ASSUMING THAT WE HAVE A REQUEST OBJECT
//                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections").child(Global.currentUser.getUuid()).child("INSERT requestUID from Request class here");
//                        dbRef.setValue("Insert Request Object here").addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Requests").child(Global.currentUser.getUuid()).child("INSERT requestUID from Request class here");
//                                db.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Log.d("Sajjad_Ali","It is done");
//                                    }
//                                });
//                            }
//                        });
//                    }
//                    else{
//                        Log.d("Sajjad_Ali","Rejected");
//                    }
            }
        });

        devicesList.setVisibility(View.VISIBLE);
        requestsList.setVisibility(View.GONE);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    devicesList.setVisibility(View.VISIBLE);
                    requestsList.setVisibility(View.GONE);
                }
                else{
                    requestsList.setVisibility(View.VISIBLE);
                    devicesList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return myView;
    }

    @Override
    public void onResume() {
        connections.clear();
        requestees.clear();
        addList();
        super.onResume();
    }

    public void addList()
    {
        //Add list of connections from FIREBASE here

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Requests").child(Global.currentUser.getUuid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Request req = ds.getValue(Request.class);
                    requestees.add(req);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        dbRef.addListenerForSingleValueEvent(valueEventListener);



        //connections.add(new User("Asharib Nadeem","7-12-2017","08:11",null));
        //connections.add(new User("Haroon Ahmed","7-12-2017","06:06",null));
        //connections.add(new User("Kashif Ahmed","7-12-2017","00:56",null));
        //connections.add(new User("Osama Haroon","6-12-2017","00:10",null));
        //connections.add(new User("Saad Mujeeb","4-12-2017","23:22",null));
        //connections.add(new User("Syed Sajjad Ali","4-12-2017","19:45",null));
        //connections.add(new User("Syeda Zainab Bukhari","4-12-2017","06:04",null));
        //connections.add(new User("Tahir Ali","3-12-2017","03:58",null));
        //connections.add(new User("Usama Aslam","29-11-2017","18:11",null));
        //connections.add(new User("Usman Mohammad Rafi","27-11-2017","15:11",null));
        //connections.add(new User("Zainab Saif","25-11-2017","08:59",null));
    }

}
