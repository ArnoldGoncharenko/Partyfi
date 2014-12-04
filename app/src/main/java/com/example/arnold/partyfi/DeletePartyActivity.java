package com.example.arnold.partyfi;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DeletePartyActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_party);

        final PDBAdapter db = new PDBAdapter(this);
        List<String> parts = new ArrayList<String>();
        db.open();

        Cursor c = db.getAllParties();
        displayCursor(c);
        TextView eText = (TextView) findViewById(R.id.parties);
        eText.setText("Would you like to get rid of a party?");


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            // The Cursor is now set to the right position
            parts.add(c.getString(0));
        }

        ListView lView = getListView();

        lView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // one choice

        lView.setTextFilterEnabled(true); // filter the children according to user input
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_list, parts);
        //setListAdapter(new ArrayAdapter<String>(this, R.layout.item_list, parts));
        lView.setAdapter(adapter);

        db.close();

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, final long id) {
                final String item = (String) parent.getItemAtPosition(position);

                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                db.open();
                                try {
                                    int myNum = Integer.parseInt(item);
                                    db.deleteParty(myNum);
                                    adapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                } catch(NumberFormatException nfe) {
                                    System.out.println("Could not parse " + nfe);
                                }finally {
                                    db.close();
                                    finish();
                                    startActivity(getIntent());
                                }
                            }
                        });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_party, menu);
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


    private void displayCursor( Cursor c ){

        if (c.moveToFirst())
        {
            do {
                displayParty(c);
            } while (c.moveToNext());
        }
    }
    private void displayParty( Cursor c )
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Title: " + c.getString(4) + "\n" +
                        //"Address:  " + c.getString(5) + "\n" +
                        "Desc: " + c.getString(3),
                Toast.LENGTH_LONG).show();
    }
}
