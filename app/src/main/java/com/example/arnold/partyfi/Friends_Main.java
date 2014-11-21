package com.example.arnold.partyfi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Friends_Main extends ActionBarActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_friends_options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void findFriend(View view)
    {
        Intent intent = new Intent(this, Friend_Main_Search1.class);
        startActivity(intent);
    }

    public void FriendsList(View view)
    {
        Intent intent = new Intent(this, Friend_Main_FriendsList.class);
        startActivity(intent);
    }
}
