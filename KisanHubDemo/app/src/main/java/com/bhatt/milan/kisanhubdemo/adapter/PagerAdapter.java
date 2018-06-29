package com.bhatt.milan.kisanhubdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bhatt.milan.kisanhubdemo.GeoLocationFragment;
import com.bhatt.milan.kisanhubdemo.TemperatureFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TemperatureFragment();
        } else if (position == 1) {
            return new GeoLocationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Temperature";
        } else if (position == 1) {
            return "GeoLocation";
        }
        return super.getPageTitle(position);
    }
}
