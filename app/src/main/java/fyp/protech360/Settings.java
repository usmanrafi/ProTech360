package fyp.protech360;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aliyan on 11/27/2017.
 */

public class Settings extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Settings");



        return myView;
    }


}
