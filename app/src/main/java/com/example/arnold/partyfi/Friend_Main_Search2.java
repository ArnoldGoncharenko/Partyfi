package com.example.arnold.partyfi;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Friend_Main_Search2 extends ListActivity {

    private DBAdapter db;

    ArrayList<String> friendsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__main__search2);

        // use the (user-defined) helper class to access an SQLite database
        // - the database is created for access

        db = new DBAdapter(this);
        db.open();

        Intent intent = getIntent();

        String name = intent.getStringExtra("friendToSearch");

        Cursor c = db.getAllContacts();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            if (c.getString(1).equals(name))
            {
                friendsList.add(c.getString(1));
            }
        }

        ListView friendsListView = getListView();

        friendsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        friendsListView.setTextFilterEnabled(true);

        setListAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_each_item, friendsList));

        db.close();
        //connect to DB based on the previous intents friendtosearch attribute.
        //use an SQL quary and display all relevant data
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend__main__search2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onListItemClick(ListView parent, View v, int position, long id )
    {
        Intent intent = new Intent(this, Friend_Profile.class);
        String val=(String)(parent.getItemAtPosition(position));
        intent.putExtra("Name", val);
        startActivity(intent);
    }
}
