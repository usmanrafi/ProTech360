package fyp.protech360.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL;

import fyp.protech360.R;
import fyp.protech360.classes.Request;
import fyp.protech360.classes.User;
import fyp.protech360.utils.Global;


public class AddConnection extends Fragment {
    View myView;
    LinearLayout smartPhoneList, wearableDeviceList;
    Button pair, cancel;
    TabLayout tabLayout;
    SeekBar mSeekbar;
    TextView mSeekText;
    Switch mSafetySwitch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addconnection,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Add a Device");

        smartPhoneList = (LinearLayout) myView.findViewById(R.id.smartphoneList);
        wearableDeviceList = (LinearLayout) myView.findViewById(R.id.wearableList);

        pair = (Button) myView.findViewById(R.id.connAddButton);
        cancel = (Button) myView.findViewById(R.id.connCancelButton);
        tabLayout = (TabLayout) myView.findViewById(R.id.tab);
        mSafetySwitch = (Switch) myView.findViewById(R.id.safetySwitch);
        mSeekbar = (SeekBar) myView.findViewById(R.id.safetySeekbar);
        mSeekText = (TextView) myView.findViewById(R.id.seekText);

        mSafetySwitch.setChecked(false);
        mSeekbar.setEnabled(false);

        mSafetySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mSeekbar.setEnabled(true);
                    mSeekbar.setProgress(0);
                    mSeekText.setText("0");
                }
                else{
                    mSeekbar.setEnabled(false);
                    mSeekText.setText("N/A");
                }
            }
        });

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Add a connection
                int selectedTab = tabLayout.getSelectedTabPosition();
                if(selectedTab == 0){
                    EditText connectionEmail = (EditText) smartPhoneList.findViewById(R.id.connEmail);
                    RadioGroup connectionType = (RadioGroup) smartPhoneList.findViewById(R.id.connType);
                    int selectedRadioButton = connectionType.getCheckedRadioButtonId();
                    int trackingType;
                    if(selectedRadioButton == R.id.radioButton) trackingType = 0;
                    else trackingType = 1;

                    requestPairing(connectionEmail.getText().toString(),trackingType);

                }
                else{
                    //TODO: ADD WEARABLE DEVICE
                    Log.d("Sajjad_Ali"," Wearable Stuff");

                }
                Toast.makeText(getActivity(),"Pairing request has been sent",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new ConnectedDevices());

//                startActivity(new Intent(getActivity(), Activity_Connections.class));
//                getActivity().finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Add Connection Cancelled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new ConnectedDevices());
//                startActivity(new Intent(getActivity(), Activity_Connections.class));
//                getActivity().finish();
            }
        });

        smartPhoneList.setVisibility(View.VISIBLE);
        wearableDeviceList.setVisibility(View.GONE);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    smartPhoneList.setVisibility(View.VISIBLE);
                    wearableDeviceList.setVisibility(View.GONE);
                }
                else{
                    wearableDeviceList.setVisibility(View.VISIBLE);
                    smartPhoneList.setVisibility(View.GONE);
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

    private void requestPairing(final String email, final int trackingType) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference usersRef = rootRef.child("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final User info = ds.getValue(User.class);
                    if(info.getEmail().equals(email) && !info.getEmail().equals(Global.currentUser.getEmail())){
                        String requestID = Global.currentUser.getUuid();
                        Request newRequest;
                        if(mSeekText.getText() == "N/A")
                            newRequest = new Request(requestID,Global.currentUser.getEmail(), info.getEmail(),trackingType);
                        else newRequest = new Request(requestID,Global.currentUser.getEmail(),info.getEmail(),trackingType, Integer.parseInt(mSeekText.getText().toString()));
                        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("Requests").child(info.getUuid()).child(requestID);
                        requestRef.setValue(newRequest)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        DatabaseReference r = FirebaseDatabase.getInstance().getReference("RequestNotify").child(Global.currentUser.getUuid()+info.getUuid());
                                        HashMap<Object,String> map = new HashMap<>();
                                        map.put("Name",Global.currentUser.getName());
                                        map.put("This",info.getUuid());
                                        r.setValue(map);

                                        r.removeValue();
                                    }

                                });
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(eventListener);

    }
}
