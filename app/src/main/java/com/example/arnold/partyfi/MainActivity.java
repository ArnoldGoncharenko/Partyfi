package com.example.arnold.partyfi;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.view.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------------------------start DB----------------------------------------------------------\\

        // use the (user-defined) helper class to access an SQLite database
        // - the database is created for access
        DBAdapter db = new DBAdapter(this);
        PDBAdapter accountDB = new PDBAdapter(this);

        //--- 1. add two contacts to the database ---
        db.open();
        accountDB.open();

        Cursor contactNum = db.getNumOfData();

        if (contactNum.getInt(0) == 0)
        {
            db.insertContact( "Arnold", "19", "True" );
            db.insertContact( "James", "28", "True" );
            db.insertContact( "Rafid","20","False" );
        }

        Cursor nameTest = accountDB.getNumOfData();

        if (nameTest.getInt(0) == 0)
        {
            accountDB.createAccount("arnold","arnold");
            accountDB.createAccount("james","james");
            accountDB.createAccount("rafid","rafid");
        }

        db.close();

        //----------------------------------------------End DB----------------------------------------------------------\\

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position)
            {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        //for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            //actionBar.addTab(actionBar.newTab()
                            //.setText(mAppSectionsPagerAdapter.getPageTitle(i))
                           // .setTabListener(this));
        //}
        actionBar.addTab(actionBar.newTab().setText("Friends").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Parties").setTabListener(this));
    }

    /** Called when the user clicks the Send button */
    public void getMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void createParty(View view) {
        Intent intent = new Intent(this, CreatePartyActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void deleteParty(View view) {
        Intent intent = new Intent(this, DeletePartyActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent = getIntent();

        String login = "";

        login = intent.getStringExtra("login");

        if (login == null)
            login = "false";

        if (login.equals("true"))
        {
            switch (item.getItemId())
            {
                case R.id.action_settings:
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        else
        {
            switch (item.getItemId())
            {
                case R.id.action_settings:
                    return true;
                case R.id.login:
                    goToLogin();
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter
    {
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
            switch (i)
            {
                case 0:
                    Fragment fragmentFriends = new DummySectionFragment();
                    Bundle argsFriends = new Bundle();
                    argsFriends.putString("test", "test");
                    fragmentFriends.setArguments(argsFriends);
                    return new LaunchpadFriendsFragment();
                case 1:
                    Fragment fragmentParties = new DummySectionFragment();
                    Bundle argsParties = new Bundle();
                    argsParties.putString("test", "test");
                    fragmentParties.setArguments(argsParties);
                    return new LaunchpadPartiesFragment();

                default:
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putString("test", "test");
                    fragment.setArguments(args);
                    return fragment;
            }
        }

        public static class LaunchpadFriendsFragment extends Fragment
        {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                View rootView = inflater.inflate(R.layout.main_friends_options, container, false);

                return rootView;
            }
        }

        public static class LaunchpadPartiesFragment extends Fragment
        {
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                View rootView = inflater.inflate(R.layout.main_party_options, container, false);

                return rootView;
            }
        }


        /**
         * A dummy fragment representing a section of the app, but that simply displays dummy text.
         */
        public static class DummySectionFragment extends Fragment
        {

            public static final String ARG_SECTION_NUMBER = "test";

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
                Bundle args = getArguments();
                ((TextView) rootView.findViewById(android.R.id.text1)).setText
                (
                        getString(R.string.action_settings, args.getInt(ARG_SECTION_NUMBER))
                );
                return rootView;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

    public void FriendsList(View view)
    {
        Intent intent = new Intent(this, Friend_Main_FriendsList.class);
        startActivity(intent);
    }

    public void goToLogin()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void FriendsSearch(View view)
    {
        Intent intent = new Intent(this, Friend_Main_Search1.class);
        startActivity(intent);
    }

}
