package fyp.protech360;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Aliyan on 12/29/2017.
 */

public class AddMeeting extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.addmeeting,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Add a Meeting");



        return myView;
    }
}