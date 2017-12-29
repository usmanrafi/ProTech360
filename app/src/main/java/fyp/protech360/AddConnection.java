package fyp.protech360;

import android.app.Fragment;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Aliyan on 12/28/2017.
 */

public class AddConnection extends Fragment {
    View myView;
    LinearLayout l1, l2;
    Button b1, b2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addconnection,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Add a Device");

        l1 = (LinearLayout) myView.findViewById(R.id.smartphoneList);
        l2 = (LinearLayout) myView.findViewById(R.id.wearableList);

        b1 = (Button) myView.findViewById(R.id.connAddButton);
        b2 = (Button) myView.findViewById(R.id.connCancelButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Pairing request has been sent",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new ConnectedDevices());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Add Connection Cancelled",Toast.LENGTH_SHORT).show();
                ((Homepage) getActivity()).setFragment(new ConnectedDevices());
            }
        });

        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.GONE);

        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.tab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                }
                else{
                    l2.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.GONE);
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
}
