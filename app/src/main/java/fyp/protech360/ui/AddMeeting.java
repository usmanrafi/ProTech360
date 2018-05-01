package fyp.protech360.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.PlacesApi;

import java.util.ArrayList;
import java.util.Calendar;

import fyp.protech360.R;
import fyp.protech360.classes.Meeting;
import fyp.protech360.classes.User;

import static android.app.Activity.RESULT_OK;

public class AddMeeting extends Fragment implements PlaceSelectionListener {
    View myView;
    Place place;

    NumberPicker n1,n2,n3,n4,n5,n6,n7;
    Button b1,b2,locationPicker;
    ImageView iv;
    String location = "";
    EditText meetingSubject;
    ArrayList<User> selectedParticipants;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addmeeting,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Schedule a Meeting");

        n1 = myView.findViewById(R.id.datePicker);
        n2 = myView.findViewById(R.id.monthPicker);
        n3 = myView.findViewById(R.id.yearPicker);
        n4 = myView.findViewById(R.id.hourPicker);
        n5 = myView.findViewById(R.id.minutePicker);
        n6 = myView.findViewById(R.id.hourPicker2);
        n7 = myView.findViewById(R.id.minutePicker2);

        initialize();

        meetingSubject = myView.findViewById(R.id.meetingSubject);
        b1 = (Button) myView.findViewById(R.id.meetingAddButton);
        b2 = (Button) myView.findViewById(R.id.meetingCancelButton);
        locationPicker = (Button) myView.findViewById(R.id.location_button);

        locationPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PLACE_PICKER_REQUEST = 1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(((Homepage) getActivity())),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(
                        n3.getValue(),n2.getValue()-1,n1.getValue(),n4.getValue(),n5.getValue(),0
                );
                Long ms = calendar.getTimeInMillis();

                String title = meetingSubject.getText().toString();

                if(getArguments() != null && !location.equals("") && !title.equals("")){
                    Meeting meeting = new Meeting(meetingSubject.getText().toString(),ms,(ArrayList<User>)getArguments().getSerializable("Selected"),location);
                    DatabaseReference addMeeting = FirebaseDatabase.getInstance().getReference("Meetings").child(meeting.getUuid());
                    addMeeting.setValue(meeting);
                    ((Homepage) getActivity()).setFragment(new Meetings());
                }
                else
                {
                    Toast.makeText(getActivity(),"Add all details before adding the meeting",Toast.LENGTH_LONG).show();
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Meeting Schedule Cancelled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new Meetings());
            }
        });

        iv = (ImageView) myView.findViewById(R.id.addParticipantsToMeeting);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("Caller","Meeting");
                Fragment fragment = new SelectParticipant();
                fragment.setArguments(b);
                ((Homepage) getActivity()).setFragment(fragment);
            }
        });




        return myView;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK){

                place = PlacePicker.getPlace(((Homepage) getActivity()),data);
                location = String.valueOf(place.getLatLng().latitude)+","+String.valueOf(place.getLatLng().longitude);
                Toast.makeText(getActivity(),"Meeting has been successfully scheduled",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initialize() {

        Calendar cal = Calendar.getInstance();
        n1.setMinValue(1);
        n2.setMinValue(1);
        n3.setMinValue(cal.get(Calendar.YEAR));
        n4.setMinValue(00);
        n5.setMinValue(00);

        n3.setMaxValue(2058);
        n1.setMaxValue(31);
        n2.setMaxValue(12);
        n4.setMaxValue(23);
        n5.setMaxValue(59);
        n6.setMaxValue(23);
        n7.setMaxValue(59);

        n4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                n6.setMinValue(newVal);
                n7.setMinValue(n5.getValue()+1);
            }
        });

        n5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(n4.getValue() == n6.getValue())
                    n7.setMinValue(newVal+1);
                else
                    n7.setMinValue(00);
            }
        });

        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if(n1.getValue() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && n2.getValue() == Calendar.getInstance().get(Calendar.MONTH)+1 && n3.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                    n4.setMinValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    n5.setMinValue(Calendar.getInstance().get(Calendar.MINUTE));
                    n6.setMinValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    n7.setMinValue(Calendar.getInstance().get(Calendar.MINUTE)+1);
                }
                else{
                    n4.setMinValue(0);
                    n5.setMinValue(0);
                    n6.setMinValue(0);
                    n7.setMinValue(1);
                }

            }
        });

        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(n3.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                    if(newVal == Calendar.getInstance().get(Calendar.MONTH)+1){
                        n1.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    }
                    else{
                        n1.setMinValue(1);
                    }
                }

                if(n1.getValue() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && n2.getValue() == Calendar.getInstance().get(Calendar.MONTH)+1 && n3.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                    n4.setMinValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    n5.setMinValue(Calendar.getInstance().get(Calendar.MINUTE));
                    n6.setMinValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    n7.setMinValue(Calendar.getInstance().get(Calendar.MINUTE)+1);
                }
                else{
                    n4.setMinValue(0);
                    n5.setMinValue(0);
                    n6.setMinValue(0);
                    n7.setMinValue(0);
                }

                if(newVal == 1 || newVal == 3 || newVal == 5 || newVal == 7 || newVal == 8 || newVal == 10 || newVal == 12)
                    n1.setMaxValue(31);
                else if(newVal == 4 || newVal == 6 || newVal == 9 || newVal == 11)
                    n1.setMaxValue(30);
                else {
                    if(n3.getValue() % 4 == 0 )
                        n1.setMaxValue(29);
                    else
                        n1.setMaxValue(28);
                }
                n1.setValue(1);
            }
        });

        n3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {


            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == Calendar.getInstance().get(Calendar.YEAR)) {
                    n2.setMinValue(Calendar.getInstance().get(Calendar.MONTH)+1);
                    if(n2.getValue() == Calendar.getInstance().get(Calendar.MONTH)+1){
                        n1.setMinValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                    }
                    else{
                        n1.setMinValue(1);
                    }
                }
                else{
                    n1.setMinValue(1);
                    n2.setMinValue(1);
                }


                if(n1.getValue() == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && n2.getValue() == Calendar.getInstance().get(Calendar.MONTH)+1 && n3.getValue() == Calendar.getInstance().get(Calendar.YEAR)){
                    n4.setMinValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    n5.setMinValue(Calendar.getInstance().get(Calendar.MINUTE));
                    n6.setMinValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                    n7.setMinValue(Calendar.getInstance().get(Calendar.MINUTE)+1);
                }
                else{
                    n4.setMinValue(0);
                    n5.setMinValue(0);
                    n6.setMinValue(0);
                    n7.setMinValue(1);
                }


                if(newVal % 4 == 0 && n2.getValue() == 2)
                    n1.setMaxValue(29);
                else if(newVal % 4 != 0 && n2.getValue() == 2)
                    n1.setMaxValue(28);
            }
        });


        n6.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal == n4.getValue())
                    n7.setMinValue(n5.getValue()+1);
                else
                    n7.setMinValue(0);
            }
        });

        n1.setValue(cal.get(cal.DAY_OF_MONTH));
        n2.setValue(cal.get(cal.MONTH)+1);
        n3.setValue(cal.get(cal.YEAR));
        n4.setValue(cal.get(cal.HOUR_OF_DAY));
        n5.setValue(cal.get(cal.MINUTE));
        n6.setValue(cal.get(cal.HOUR_OF_DAY));
        n7.setValue(cal.get(cal.MINUTE));

        n4.setMinValue(n4.getValue());
        n5.setMinValue(n5.getValue());
        n6.setMinValue(n4.getValue());
        n7.setMinValue(n5.getValue()+1);


        n1.setWrapSelectorWheel(true);
        n2.setWrapSelectorWheel(true);
        n3.setWrapSelectorWheel(true);
        n4.setWrapSelectorWheel(true);
        n5.setWrapSelectorWheel(true);
        n6.setWrapSelectorWheel(true);
        n7.setWrapSelectorWheel(true);


    }

    @Override
    public void onPlaceSelected(Place place) {

    }

    @Override
    public void onError(Status status) {

    }
}