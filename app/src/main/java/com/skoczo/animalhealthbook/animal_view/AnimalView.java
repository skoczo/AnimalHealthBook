package com.skoczo.animalhealthbook.animal_view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoczo.animalhealthbook.R;
import com.skoczo.animalhealthbook.animal_view.info.AnimalInfo;

public class AnimalView extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String key;
    private AnimalInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.container);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                // TODO:
                key = null;
            } else {
                key = extras.getString("key");
            }
        } else {
            key = (String) savedInstanceState.getSerializable("key");
            mViewPager.setCurrentItem(savedInstanceState.getInt("item"));
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        if (mViewPager.getAdapter() == null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager.setAdapter(mSectionsPagerAdapter);


            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("item", mViewPager.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animal_view, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements DynamicFabUpdater {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_animal_view, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }

        @Override
        public void fabUpdate() {
//            FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.animal_view_fab);
//            fab.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_menu_add));
//            fab.setVisibility(View.VISIBLE);
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Fragment[] items = new Fragment[getCount()];

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return AnimalInfo.newInstance(key);
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Costs";
                case 2:
                    return "?";
            }
            return null;
        }
    }
}
