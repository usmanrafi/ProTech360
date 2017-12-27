package fyp.protech360;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Alerts extends Fragment {
    View myView;
    SearchView searchView;
    ListView listView;
    ArrayList<AlertDetail> alerts = new ArrayList<>();
    CustomAdapter alertAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.alerts,container,false);
        alertAdapter = new CustomAdapter(getActivity(),R.layout.alert_row,alerts);
        ((Homepage) getActivity()).setActionBarTitle("Alerts");
        setHasOptionsMenu(true);
        listView = (ListView) myView.findViewById(R.id.alertList);

        listView.setAdapter(alertAdapter);


        return myView;
    }

    @Override
    public void onResume() {
        alerts.clear();
        addList();
        super.onResume();
    }

    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }



    public void addList()
    {
        alerts.add(new AlertDetail("Sajjad has gone out of safety range","6-12-2017","18:11"));
        alerts.add(new AlertDetail("Usman has gone out of safety range","6-12-2017","15:06"));
        alerts.add(new AlertDetail("It looks like Zainab is near water, go check it out","6-12-2017","08:56"));
        alerts.add(new AlertDetail("Waqar has gone out of safety range","6-12-2017","00:10"));
        alerts.add(new AlertDetail("Maham's speed has changed drastically, keep a check","4-12-2017","23:22"));
        alerts.add(new AlertDetail("Osama has gone out of safety range","4-12-2017","19:45"));
        alerts.add(new AlertDetail("Haroon has gone out of safety range","4-12-2017","06:04"));
        alerts.add(new AlertDetail("Sajjad has gone out of safety range","3-12-2017","03:58"));
        alerts.add(new AlertDetail("Rana has gone out of safety range","29-11-2017","18:11"));
        alerts.add(new AlertDetail("Saad has gone out of safety range","27-11-2017","15:11"));
        alerts.add(new AlertDetail("Hussam has gone out of safety range","25-11-2017","08:59"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.searchbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.detach(Alerts.this).attach(Alerts.this).commit();
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } else
                    alertAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }


}
