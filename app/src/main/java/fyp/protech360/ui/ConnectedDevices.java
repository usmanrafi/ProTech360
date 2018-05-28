package fyp.protech360.ui;

        import android.app.AlertDialog;
        import android.app.Fragment;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;
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
        import android.widget.ProgressBar;
        import android.widget.TableLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

        import fyp.protech360.R;
        import fyp.protech360.classes.Request;
        import fyp.protech360.classes.User;
        import fyp.protech360.dal.DatabaseHelper;
        import fyp.protech360.dal.FirebaseHelper;
        import fyp.protech360.utils.ConnectionAdapter;
        import fyp.protech360.utils.Global;
        import fyp.protech360.utils.RequestAdapter;

        import static fyp.protech360.utils.Global.currentUser;

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
  ProgressBar pb;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    myView = inflater.inflate(R.layout.connected_devices,container,false);
//        ((Homepage) getActivity()).setActionBarTitle("Devices");

    pb = myView.findViewById(R.id.devicesProgress);
    pb.setVisibility(View.VISIBLE);

    mTabLayout = myView.findViewById(R.id.tab);

    fab =  myView.findViewById(R.id.addConnection);
    devicesList = myView.findViewById(R.id.devicesList);
    devicesList.setClickable(true);

    devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User selectedUser = (User) deviceAdapter.getItem(position);
        ConnectionDetails connectionDetails = new ConnectionDetails();
        Bundle args = new Bundle();
        args.putString("User", selectedUser.getUuid());
        connectionDetails.setArguments(args);
        ((Homepage) getActivity()).setFragment(connectionDetails);
      }
    });

    devicesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Remove Device");
        final User user = (User) deviceAdapter.getItem(position);
        alert.setMessage("Do you want to remove " + user.getName() + " from your connections?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {

            // Do something with value!
            Log.d("Aliyan","User Removed");
            removeConnection(Global.currentUser.getUuid(),user.getUuid());
            removeConnection(user.getUuid(),Global.currentUser.getUuid());

            connections.remove(position);
            deviceAdapter.notifyDataSetChanged();
          }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
            Log.d("Aliyan","User Not Removed");
          }
        });

        alert.show();

        return true;
      }
    });

    requestsList = myView.findViewById(R.id.requestsList);
    requestsList.setClickable(true);

    deviceAdapter = new ConnectionAdapter(getActivity(), R.layout.connectionslist_row, connections);
    devicesList.setAdapter(deviceAdapter);


    requestAdapter = new RequestAdapter(getActivity(), R.layout.requests_row, requestees);
    requestsList.setAdapter(requestAdapter);

    requestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        final Request req = (Request) requestAdapter.getItem(position);
        final String reqID = req.getRequestUID();
        final Request req2 = new Request(currentUser.getUuid(),req.getRequestName(),req.getSentTo(),req.getRequestType());

        AlertDialog.Builder confirm = new AlertDialog.Builder(getActivity());
        confirm.setTitle("Connection Request");

        //Todo: Add Email in Request

        final TextView name = new TextView(getActivity());
        name.setText("Name: \t\t" + req.getRequestName() + "\n\n");

        final TextView type = new TextView(getActivity());
        type.setText("Type: \t\t" + trackingType(req.getRequestType()));

        final LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(name);
        layout.addView(type);

        confirm.setView(layout);

        confirm.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Log.d("Usman", "OnClick Confirm Request");

            requestees.remove(position);
            requestAdapter.notifyDataSetChanged();
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections").child(reqID).child(currentUser.getUuid());
            dbRef.setValue(req).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                if (req.getRequestType() == 1) {
                  DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections").child(currentUser.getUuid()).child(reqID);
                  dbRef.setValue(req2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      Log.d("Usman", "Two-way");
                    }
                  });

                }
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Requests").child(currentUser.getUuid()).child(reqID);
                db.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Usman", "It is done");
                  }
                });
              }
            });
          }
        });

        confirm.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            requestees.remove(position);
            requestAdapter.notifyDataSetChanged();

            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Requests").child(currentUser.getUuid()).child(reqID);
            db.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                Log.d("Usman", "It is done");
              }
            });
          }
        });

        confirm.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

          }
        });


        confirm.show();
      }
    });


    devicesList.setVisibility(View.VISIBLE);
    requestsList.setVisibility(View.GONE);


    mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition() == 0){
          fab.setVisibility(View.VISIBLE);
          devicesList.setVisibility(View.VISIBLE);
          requestsList.setVisibility(View.GONE);
        }
        else{
          fab.setVisibility(View.GONE);
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

  private void removeConnection(String conn1, String conn2) {
    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Connections").child(conn1).child(conn2);
    dbref.removeValue();
  }

  @Override
  public void onResume() {
    addList();

    super.onResume();
  }



  public void addList()
  {
    loadUsers();
    loadRequests();
  }

  private void loadRequests() {
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Requests").child(Global.currentUser.getUuid());
    dbRef.addValueEventListener(new ValueEventListener() {

      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        requestees.clear();
        ArrayList<Request> requests = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
          Request req = ds.getValue(Request.class);
          requests.add(req);
        }
        requestees.addAll(requests);
        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        tab.setText("Requests - " + requestees.size());
        Log.d("Sajjad_Ali","Size: " +requestees.size());
        requestAdapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  public String trackingType(int type){
    if(type == 0){
      return "One-way Tracking";
    }
    else{
      return "Two-way Tracking";
    }
  }


  public void loadUsers(){


    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Connections")
            .child(Global.currentUser.getUuid());


    dbRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        final ArrayList<String> uids = new ArrayList<>();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
          uids.add(ds.getKey());
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        userRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            Global.currentUser.initConnections();
            for (DataSnapshot ds : dataSnapshot.getChildren()){
              User users = ds.getValue(User.class);
              if(uids.indexOf(users.getUuid()) != -1) {
                Global.currentUser.addConnection(users);
              }
            }

            connections.clear();
            connections.addAll(Global.currentUser.getConnections());
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Connections - " + connections.size());
            pb.setVisibility(View.GONE);
            deviceAdapter.notifyDataSetChanged();

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }


}
