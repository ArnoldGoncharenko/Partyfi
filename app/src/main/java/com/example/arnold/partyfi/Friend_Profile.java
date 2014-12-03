package com.example.arnold.partyfi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Friend_Profile extends Activity {

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__profile);

        Intent intent = getIntent();
        final String position = intent.getStringExtra("Slot");

        int finalPosition = Integer.parseInt(position);

        db = new DBAdapter(this);
        db.open();

        Cursor c = db.getContact(finalPosition+1);

        String name="";
        String age="";
        String friends="";

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            name = c.getString(0);
            age = c.getString(1);
            friends = c.getString(2);
        }

        TextView t = (TextView)findViewById(R.id.Name);
        t.setText(name);

        t = (TextView)findViewById(R.id.Age);
        t.setText(age);

        t = (TextView)findViewById(R.id.Friends);
        t.setText(friends);

        //Quarry the server for that person and then fill up the textviews with info
        //as well as grey out "add friend" buttons if already friend
        //grey out "Remove friend" and "invite friend" if not friends

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend__profile, menu);
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
}
