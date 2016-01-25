package com.example.francois.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Francois on 25/01/2016.
 */
public class ProfilAdapter extends FragmentStatePagerAdapter {
    public ProfilAdapter(FragmentManager fm) {
        super(fm);
    }

    private static final int NUM_PAGES = 3;

    @Override
    public Fragment getItem(int position) {
        Fragment tmp = null;
        if (position == 1) {
            tmp = new ProjetsFragment();
        } else if (position == 3) {
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