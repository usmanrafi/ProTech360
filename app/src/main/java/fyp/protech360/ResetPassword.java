package fyp.protech360;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResetPassword extends Fragment {
    View myView ;
    android.app.FragmentManager fragmentManager = getFragmentManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.resetpassword,container,false);



        return myView;
    }

}
