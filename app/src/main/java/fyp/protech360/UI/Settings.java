package fyp.protech360.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fyp.protech360.R;
import fyp.protech360.UI.Homepage;
import fyp.protech360.Utils.Global;

public class Settings extends Fragment {
    View myView;

    EditText name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings, container, false);
        ((Homepage) getActivity()).setActionBarTitle("Settings");

        name = myView.findViewById(R.id.editName);

        name.setText(Global.currentUser.getName());
        return myView;
    }
}
