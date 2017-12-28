package fyp.protech360;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

public class Homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    android.app.FragmentManager fragmentManager = getFragmentManager();
    boolean toggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Home()).commit();
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
            case R.id.nav_conversations:
                fragmentManager.beginTransaction().replace(R.id.content_frame,new connectionDetails()).commit();
                break;
            case R.id.nav_trackrooms:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new TrackRooms()).commit();
                startActivity(new Intent(this,TrackroomDetails.class));
                break;
            case R.id.nav_alerts:
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Alerts()).commit();
                break;
            case R.id.nav_meetings:
                //startActivity(new Intent(this,MeetingDetails.class));
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Meetings()).commit();
                break;
            case R.id.nav_reminders:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Reminders()).commit();
                break;
            case R.id.nav_scheduled_settings:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduledSettings()).commit();
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
            Toast.makeText(getApplication(),"Profile Updated",Toast.LENGTH_SHORT).show();
            toggle = false;
        }

    }

    public void editMessage(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Edit Emergency Message");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                Toast.makeText(getApplicationContext(),"Emergency Message Updated",Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                Toast.makeText(getApplicationContext(),"Emergency Message Update Cancelled",Toast.LENGTH_SHORT).show();
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

                // Do something with value!
                Toast.makeText(getApplicationContext(),"SignOut Successful",Toast.LENGTH_SHORT).show();
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
}

