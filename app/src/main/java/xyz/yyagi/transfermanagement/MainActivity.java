package xyz.yyagi.transfermanagement;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements ActionBar.TabListener {

    ViewPager mViewPager;
    AbsListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(adapter.getPageTitle(i))
                    .setTabListener(this));
        }

        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private int mTabCount = 2;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ItemFragment.newInstance();
                case 1:
                    return RegisterFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return mTabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_list).toUpperCase(l);
                case 1:
                    return getString(R.string.title_add).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab,
                              FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
                                FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
                                FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void resetTransferDataList() {
        List<TransferData> mDataList = TransferData.findWithQuery(TransferData.class, "select * from Transfer_data order by id DESC limit 20");
        ListAdapter mAdapter = new ArrayAdapter<TransferData>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mDataList);
        if (mListView == null) {
            mListView = (AbsListView) findViewById(android.R.id.list);
        }
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
    }


    public static class SimpleFragment extends Fragment {

        public static SimpleFragment getInstance(int position) {
            SimpleFragment f = new SimpleFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            f.setArguments(args);
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_simple, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            int position = getArguments().getInt("position");
            TextView tv = (TextView) view.findViewById(R.id.title);
            tv.setText("Page " + (position + 1));
        }
    }

}
