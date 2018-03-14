package fyp.protech360.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fyp.protech360.R;

public class ChangePassword extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.changepassword,container,false);
        ((Homepage) getActivity()).setActionBarTitle("Change Password");



        return myView;
    }

}
