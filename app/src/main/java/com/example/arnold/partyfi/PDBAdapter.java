package com.example.arnold.partyfi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James Laverty
 */
public class PDBAdapter
{
    // Logcat tag
    private static final String LOG = "DatabaseHelp";

    // Database Version
    private static final int DATABASE_VERSION = 8;

    // Database Name
    private static final String DATABASE_NAME = "partyManager";
    //Table Names
    private static final String TABLE_PARTY = "party";
    private static final String TABLE_ACCOUNT = "account";
    // Common column names
    private static final String KEY_ID = "_id";
    // PARTY Table - column names
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_ADDR = "address";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";


    // ACCOUNT Table - column names
    private static final String KEY_UNAME = "uname";
    private static final String KEY_PWORD = "pword";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    // Table Create Statements
    // Party table create statement
    static final String CREATE_TABLE_PARTY = "CREATE TABLE "
            + TABLE_PARTY + "( _id integer primary key autoincrement," + KEY_LAT
            + " DOUBLE," + KEY_LNG + " DOUBLE," + KEY_TITLE
            + " TEXT," + KEY_DESC + " TEXT," + KEY_ADDR + " TEXT,"
            + KEY_DATE + " TEXT," + KEY_TIME + " TEXT);";

    // Account table create statement
    static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE "
            + TABLE_ACCOUNT + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_UNAME
            + " TEXT," + KEY_PWORD + " TEXT" + ");";

    public PDBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

            // creating required tables
            db.execSQL(CREATE_TABLE_PARTY);
            db.execSQL(CREATE_TABLE_ACCOUNT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);

            // create new tables
            onCreate(db);
        }
    }
    public long createParty(Party party)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, party.lat);
        values.put(KEY_LNG, party.lng);
        values.put(KEY_TITLE, party.title);
        values.put(KEY_DESC, party.description);
        values.put(KEY_ADDR, party.address);
        values.put(KEY_DATE, party.date);
        values.put(KEY_TIME, party.time);


        long party_id = db.insert(TABLE_PARTY, null, values);

        return party_id;
    }

    public PDBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        DBHelper.close();
    }
    public Party getParty(long party_id)
    {
        String selectQuery = "SELECT  * FROM " + TABLE_PARTY + " WHERE "
                + KEY_ID + " = " + party_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Party p = new Party();
        p.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        p.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));
        p.setLat(c.getDouble(c.getColumnIndex(KEY_LAT)));
        p.setLng(c.getDouble(c.getColumnIndex(KEY_LNG)));
        p.setAddress(c.getString(c.getColumnIndex(KEY_ADDR)));
        p.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        p.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
        p.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));

        return p;
    }

    public Cursor getAllParties()
    {
        return db.query(TABLE_PARTY,
                new String[]{KEY_ID, KEY_LAT, KEY_LNG, KEY_DESC, KEY_TITLE, KEY_ADDR, KEY_DATE, KEY_TIME},
                null, null, null, null, null);
    }

    public void deleteParty(long party_id)
    {
        db.delete(TABLE_PARTY, KEY_ID + " = ?",
                new String[]{String.valueOf(party_id)});
    }


    //-----------------------------------Create account----------------------------------------//

    public long createAccount(String username, String password)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_UNAME, username);
        values.put(KEY_PWORD, password);

        return db.insert( TABLE_ACCOUNT, null, values );
    }

    public Cursor getAccount(String username) throws SQLException
    {
        //Cursor mCursor = db.query( true,DATABASE_TABLE,new String[] {KEY_ROWID, KEY_NAME, KEY_AGE, KEY_FRIENDS},KEY_ROWID + "=" + rowId,null, null, null, null, null, null );

        Cursor mCursor = db.rawQuery("SELECT COUNT(*) FROM account", null);

        try
        {
            mCursor = db.rawQuery("SELECT " + KEY_UNAME + " , " + KEY_PWORD + " FROM account WHERE " + KEY_PWORD + " = " + username,null);
            if ( mCursor != null ) { mCursor.moveToFirst(); } // move the cursor to the first row
            return mCursor;
        }
        catch (SQLException e)
        {
            mCursor = db.rawQuery("SELECT COUNT(*) FROM account", null);
        }

        finally
        {
            if ( mCursor != null ) { mCursor.moveToFirst(); } // move the cursor to the first row
            return mCursor;
        }
    }

    public Cursor getAllAccounts() throws SQLException
    {
        return db.query( TABLE_ACCOUNT , new String[] { KEY_UNAME, KEY_PWORD },null, null, null, null, null, null);
    }

    // closing database

    public Cursor getNumOfData() throws SQLException
    {
        Cursor NumData = db.rawQuery("SELECT COUNT(*) FROM account", null);

        if ( NumData != null ) { NumData.moveToFirst(); } // move the cursor to the first row

        return NumData;
    }


}