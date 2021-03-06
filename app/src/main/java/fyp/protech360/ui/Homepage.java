package fyp.protech360.ui;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.UUID;

import javax.microedition.khronos.opengles.GL;

import de.hdodenhof.circleimageview.CircleImageView;
import fyp.protech360.dal.DatabaseHelper;
import fyp.protech360.classes.EmergencyDetails;
import fyp.protech360.R;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    android.app.FragmentManager fragmentManager = getFragmentManager();
    boolean toggle = false;
    DatabaseHelper databaseHelper;

    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 3;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;

    private TextView name;
    private TextView email;
    private CircleImageView image;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Usman_Homepage","onCreate of Homepage.java");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Global.initDB(getApplicationContext());
        databaseHelper = Global.dbHelper;
        permissions();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //if(mFirebaseUser == null || !(mFirebaseUser.isEmailVerified())) {
        //    startActivity(new Intent(getApplicationContext(), VerificationActivity.class));
        //    finish();
        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                loadProfile();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.username);
        email = (TextView) header.findViewById(R.id.usernumber);
        image = (CircleImageView) header.findViewById(R.id.imageView);

        if(getIntent().getBooleanExtra("GotoTrackRoom", false))
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TrackRoom())
                    .commit();
        else{
            //Global.currentUser.clearConnections();
            //new ConnectedDevices().loadUsers();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Home()).commit();
        }
    }

    private void loadProfile() {
        Cursor res = databaseHelper.getUserData(mFirebaseUser.getUid());
        while (res.moveToNext()){
            name.setText(res.getString(0));
            email.setText(res.getString(1));

            try {
                byte[] decodedString = Base64.decode(res.getString(2), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image.setImageBitmap(decodedByte);
            }
            catch (NullPointerException ne){
                image.setImageResource(R.drawable.add);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            case R.id.nav_home:
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Home()).commit();
                break;
            case R.id.nav_connected_devices:
                fragmentManager.beginTransaction().replace(R.id.content_frame,new ConnectedDevices()).commit();
                break;
            case R.id.nav_trackrooms:
               fragmentManager.beginTransaction().replace(R.id.content_frame, new TrackRoom()).commit();
                break;
            case R.id.nav_alerts:
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Alerts()).commit();
                break;
            case R.id.nav_meetings:
                //startActivity(new Intent(this,MeetingDetails.class));
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Meetings()).commit();
                break;
            case R.id.nav_reminders:
                addReminder(null);
                break;
            case R.id.nav_scheduled_settings:
                startActivity(new Intent(this,AddScheduledProfile.class));
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduledSettings()).commit();
                break;
            case R.id.nav_settings:
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Settings()).commit();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void editName(View view) {
        EditText edit = (EditText) findViewById(R.id.editName);
        ImageView iv = (ImageView) findViewById(R.id.editNameButton);
        if(!toggle)
        {
            iv.setImageResource(R.drawable.done);
            edit.setEnabled(true);
            toggle = true;
        }
        else
        {
            iv.setImageResource(R.drawable.edit);
            edit.setEnabled(false);
            Global.currentUser.setName(edit.getText().toString());
            Toast.makeText(getApplication(),"Profile Updated",Toast.LENGTH_SHORT).show();
            toggle = false;
        }

    }

    public void editMessage(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Edit Emergency Details");

        // Setting the Dialog view

        EmergencyDetails details = Global.currentUser.getEmergencyDetails();

        final EditText text = new EditText(this);
        text.setText(details.getMessage());
        text.setMaxLines(1);

        final EditText num1 = new EditText(this);
        final EditText num2 = new EditText(this);
        final EditText num3 = new EditText(this);

        String[] nums = {details.getNum1(),details.getNum2(),details.getNum3()};
        num1.setText(nums[0]);
        num2.setText(nums[1]);
        num3.setText(nums[2]);

        num1.setInputType(InputType.TYPE_CLASS_PHONE);
        num2.setInputType(InputType.TYPE_CLASS_PHONE);
        num3.setInputType(InputType.TYPE_CLASS_PHONE);

        final LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.addView(text);
        box.addView(num1);
        box.addView(num2);
        box.addView(num3);

        alert.setView(box);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                String n1 = num1.getText().toString();
                String n2 = num2.getText().toString();
                String n3 = num3.getText().toString();

                Log.d("Sajjad_Ali",n1);
                if(n1.length() != 11 && n1.length() != 13)
                    n1 = null;
                if(n2.length() != 11 && n2.length() != 13)
                    n2 = null;
                if(n3.length() != 11 && n3.length() != 13)
                    n3 = null;


                Global.currentUser.setEmergencyDetails(new EmergencyDetails(
                        text.getText().toString(),
                        n1, n2, n3
                ));


                DatabaseHelper dbHelper = DatabaseHelper.getInstance(getApplicationContext());
                dbHelper.insertEmergencyDetails(text.getText().toString(), n1, n2, n3);

                DatabaseReference dbRef = FirebaseDatabase.getInstance()
                        .getReference("Users").child(Global.currentUser.getUuid()).child("emergencyDetails");
                dbRef.setValue(Global.currentUser.getEmergencyDetails());


                Log.d("Sajjad_Ali","Emergency Details Done " + n1);
                Toast.makeText(getApplicationContext(),"Emergency Details Updated",Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                Toast.makeText(getApplicationContext(),"Emergency Details Update Cancelled",Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }

    public void signOut(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("SignOut");
        alert.setMessage("Are you sure you want to Sign Out?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                mFirebaseAuth.signOut();
                Toast.makeText(getApplicationContext(),"SignOut Successful",Toast.LENGTH_SHORT).show();


                Global.currentUser = null;

                stopService(Global.LocationIntent);

                Intent intent = new Intent(getApplicationContext(),VerificationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                Toast.makeText(getApplicationContext(),"SignOut Cancelled",Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }

    public void deactivateAccount(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Deactivate Account");
        alert.setMessage("Are you sure you want to Deactivate your account?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                Toast.makeText(getApplicationContext(),"Account Deactivated",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),VerificationActivity.class);
                startActivity(intent);
                finish();

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                Toast.makeText(getApplicationContext(),"Cancelled",Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();

    }

    public void changepassword(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame,new ChangePassword()).commit();
    }

    public void changePassword(View view) {
        Toast.makeText(getApplication(),"Password Successfully Changed",Toast.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new Settings()).commit();
    }

    public void openAbout(View view){
        fragmentManager.beginTransaction().replace(R.id.content_frame,new About()).commit();
    }

    public void openHelp(View view){
        fragmentManager.beginTransaction().replace(R.id.content_frame,new Help()).commit();
    }

    public void addDevice(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame,new AddConnection()).commit();
    }

    public void addMeeting(View view) {
        fragmentManager.beginTransaction().replace(R.id.content_frame,new AddMeeting()).commit();
    }

    public void addRoom(View view){
        fragmentManager.beginTransaction().replace(R.id.content_frame,new AddTrackRoom()).commit();
    }

    public void addReminder(View view){
        startActivity(new Intent(this,AddReminder.class));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permissions();
                }
                else
                {

                }
                return;
            }
        }


    }

    void permissions()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }


    }
}

