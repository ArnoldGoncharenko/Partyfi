package com.example.arnold.partyfi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void LoginUser(View view)
    {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        PDBAdapter accountDB = new PDBAdapter(this);

        accountDB.open();

        Cursor loginCursor = accountDB.getAllAccounts();

        for(loginCursor.moveToFirst(); !loginCursor.isAfterLast(); loginCursor.moveToNext())
        {
            Log.d("FIRST STRING",loginCursor.getString(0));
            Log.d("SECOND STRING",loginCursor.getString(0));
            // The Cursor is now set to the right position
            if (loginCursor.getString(0).equals(username))
            {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("login", "true");
                startActivity(intent);
            }
        }

        //Cursor loginCursor = accountDB.getAccount(username);

        //if (loginCursor.getString(0) == username && loginCursor.getString(1) == password)
        //{
            //Intent intent = new Intent(this, MainActivity.class);
           // intent.putExtra("login", "true");
            //startActivity(intent);
       // }
        //else
        //{
          //  Toast toast = Toast.makeText(this, "You have typed in the wrong password", Toast.LENGTH_LONG);
           // toast.show();
        //}
    }

}
