package fyp.protech360.ui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import fyp.protech360.R;
import fyp.protech360.classes.Room;

public class TrackroomDetails extends AppCompatActivity {

    public ArrayList<Room> r = new ArrayList<>();

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    Toolbar toolbar;

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackroom_details);

        String roomID = getIntent().getStringExtra("Room");

        retrieveRoom(roomID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                toolbar.setTitle(r.get(0).getTitle());
                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(mViewPager);
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                       Fragment fr = mSectionsPagerAdapter.getItem(tab.getPosition());
                       setFragment(fr);

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

            }
        },2000);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


    }

    public void setFragment(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
    }

    private void retrieveRoom(String roomID) {

        DatabaseReference room = FirebaseDatabase.getInstance().getReference("Rooms").child(roomID);
        room.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                r.add(dataSnapshot.getValue(Room.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    Fragment fragment = new TrackRoomMaps();
                    Bundle b = new Bundle();
                    b.putSerializable("room",r);
                    fragment.setArguments(b);
                    return fragment;
                case 1:
                    Fragment fragment2 = new TrackRoomMembers();
                    Bundle b2 = new Bundle();
                    b2.putSerializable("room",r);
                    fragment2.setArguments(b2);
                    return fragment2;
                default:
                    return null;
            }
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Map";
                case 1:
                    return "Members";
            }
            return null;
        }

            @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
