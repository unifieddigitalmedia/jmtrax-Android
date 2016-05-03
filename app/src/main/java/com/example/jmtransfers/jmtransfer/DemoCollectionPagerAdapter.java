package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

/**
 * Created by UnitedDigitalLondon on 21/12/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {


    public DemoCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new DemoObjectFragment();

        Bundle args = new Bundle();

        args.putInt(DemoObjectFragment.ARG_OBJECT, i);

        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }


    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        private Integer[] Right = {

                R.drawable.usa_street_chicago
        };


        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_collection_object, container, false);

            Bundle args = getArguments();

            ((ImageView) rootView.findViewById(R.id.logo)).setImageResource(Right[args.getInt(ARG_OBJECT)]);


            return rootView;

        }

    }

}