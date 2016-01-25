package com.example.francois.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Francois on 25/01/2016.
 */
public class DashBoardAdapter extends FragmentStatePagerAdapter {
    public DashBoardAdapter(FragmentManager fm) {
        super(fm);
    }

    private static final int NUM_PAGES = 2;

    @Override
    public Fragment getItem(int position) {
        Fragment tmp;
        if (position == 1) {
            tmp = new ProjetsFragment();
        } else if (position == 2) {
            tmp = new ProfilFragment();
        } else {
            tmp = new ModulesFragment();
        }
        return tmp;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}