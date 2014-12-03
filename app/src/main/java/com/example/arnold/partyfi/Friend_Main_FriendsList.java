package com.example.arnold.partyfi;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class Friend_Main_FriendsList extends ListActivity {

    private DBAdapter db;

    ArrayList<String> friendsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__main__friends_list);

        // use the (user-defined) helper class to access an SQLite database
        // - the database is created for access

        db = new DBAdapter(this);
        db.open();

        Cursor c = db.getAllContacts();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            // The Cursor is now set to the right position
            friendsList.add(c.getString(1));
        }

        ListView friendsListView = getListView();

        friendsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        friendsListView.setTextFilterEnabled(true);

        setListAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_each_item, friendsList));

        db.close();

        //Query server and display a list of people you are friends with
        //and add an option to click on them

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend__main__friends_list, menu);
        return true;
    }

    public void onListItemClick(ListView parent, View v, int position, long id )
    {
        Intent intent = new Intent(this, Friend_Profile.class);
        String message = Integer.toString(position);
        intent.putExtra("Slot", message);
        startActivity(intent);
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
    private void displayContact( Cursor c )
    {
        Toast.makeText( this, "id: " + c.getString(1) + "\n", Toast.LENGTH_LONG).show();
    }
}
