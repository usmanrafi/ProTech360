package fyp.protech360.ui;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fyp.protech360.R;

public class Login extends Fragment {

    View myView ;
    android.app.FragmentManager fragmentManager = getFragmentManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_login,container,false);



        return myView;
    }

}