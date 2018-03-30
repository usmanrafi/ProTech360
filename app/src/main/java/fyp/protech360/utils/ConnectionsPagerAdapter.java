package fyp.protech360.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fyp.protech360.ui.ConnectedDevices;

public class ConnectionsPagerAdapter extends FragmentPagerAdapter {

    private String titles[] = new String [] {
      "Connected Devices",
      "Requests"
    };

    public ConnectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ConnectedDevices();
            case 1:
                return new ConnectedDevices();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.titles[position];
    }
}
