package com.example.arnold.partyfi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* Source: textbook by Wei Ming Lee
 * Enhanced and Annotated by Peter Liu
 */

// a user-defined helper class to access an SQLite database
public class DBAdapter {

    // String constants for the columns of a database table
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME  = "name";
    static final String KEY_AGE = "age";
    static final String KEY_FRIENDS = "friends";

    // String constants for database name, database version, and table name
    static final String DATABASE_NAME = "DBFriends";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 2;

    // SQL statement for creating a database schema
    static final String DATABASE_CREATE =
            "create table contacts ( _id integer primary key autoincrement, " +
                    "name text not null, age text not null, friends text not null );";

    static final String ACCOUNTS =
            "create table accounts ( _id integer primary key autoincrement, " +
                    "username text not null, password text not null);";

    final Context context;   // context for database access

    DatabaseHelper dBHelper; // a private static nested class

    SQLiteDatabase db;

    public DBAdapter( Context ctx )
    {
        this.context = ctx;
        dBHelper = new DatabaseHelper( context ); // private static nested class
    }

    // static nested class: create and upgrade a database
    // SQLiteOpenHelper: database creation and version management
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper( Context ctx )
        {
            super( ctx, DATABASE_NAME, null, DATABASE_VERSION );
        }

        @Override
        // onCreate() is called when the database is created for the first time.
        public void onCreate( SQLiteDatabase db )
        {
            try {
                db.execSQL( DATABASE_CREATE ); // create the database schema

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate( db ); // invoke SQLiteOpenHelper's onCreate() method
        }
    }// end DatabseHelper

    /* 7 database access methods:
     *   open(), close(), insertContact(), deleteContact(), getAllContacts(),
     *   getContact(), updateContact()
     */

    //--- 1. open the database of contacts ---
    public DBAdapter open() throws SQLException
    {
        db = dBHelper.getWritableDatabase();
        return this;
    }

    //--- 2. closes the database of contacts ---
    public void close()
    {
        dBHelper.close();
    }

    //--- 3. insert a contact into the database ---
    //       - ContentValues: key/value pairs
    public long insertContact(String name, String age, String friends)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put( KEY_NAME, name );
        initialValues.put( KEY_AGE, age );
        initialValues.put( KEY_FRIENDS, friends);

        return db.insert( DATABASE_TABLE, null, initialValues );
    }

    //--- 4. deletes a particular contact from the database ---
    //needs to be reworked to delete a contact based on name
    public boolean deleteContact(long rowId)
    {
        return db.delete( DATABASE_TABLE, KEY_ROWID + "=" + rowId, null ) > 0;
    }

    //---5. retrieves all the contacts from the databsae ---
    //      - Cursor object: a pointer to the result set of the query
    public Cursor getAllContacts()
    {
        return db.query( DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME, KEY_AGE, KEY_FRIENDS },null, null, null, null, null, null);
    }

    //--- 6. retrieve a particular contact from the database ---
    //       - Cursor object: a pointer to the result set of the query
    public Cursor getContact(long rowId) throws SQLException
    {
        //Cursor mCursor = db.query( true,DATABASE_TABLE,new String[] {KEY_ROWID, KEY_NAME, KEY_AGE, KEY_FRIENDS},KEY_ROWID + "=" + rowId,null, null, null, null, null, null );

        Cursor mCursor = db.rawQuery("SELECT name, age, friends FROM contacts WHERE _id=" + rowId,null);

        if ( mCursor != null ) { mCursor.moveToFirst(); } // move the cursor to the first row

        return mCursor;
    }

    public Cursor getContactByName(String name) throws SQLException
    {
        //Cursor mCursor = db.query( true,DATABASE_TABLE,new String[] {KEY_ROWID, KEY_NAME, KEY_AGE, KEY_FRIENDS},KEY_ROWID + "=" + rowId,null, null, null, null, null, null );

        Cursor mCursor = db.rawQuery("SELECT name, age, friends FROM contacts WHERE name=" + name,null);

        if ( mCursor != null ) { mCursor.moveToFirst(); } // move the cursor to the first row

        return mCursor;
    }

    //--- 7. updates a contact in the database ---
    public boolean updateContact(long rowId, String name, String age, String friends)
    {
        ContentValues args = new ContentValues();  // key/value pairs
        args.put(KEY_NAME, name);
        args.put(KEY_AGE, age);
        args.put(KEY_FRIENDS, friends);

        return db.update( DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getNumOfData() throws SQLException
    {
        Cursor NumData = db.rawQuery("SELECT COUNT(*) FROM contacts", null);

        if ( NumData != null ) { NumData.moveToFirst(); } // move the cursor to the first row

        return NumData;
    }
} // end DBAdapter
